// <editor-fold desc="The MIT License" defaultstate="collapsed">
/* 
 * The MIT License
 * 
 * Copyright 2021 Studio 42 GmbH ( https://www.s42m.de ).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
//</editor-fold>
package de.s42.dl.examples.dlonly;

import de.s42.base.conversion.ConversionHelper;
import de.s42.dl.DLAnnotation;
import de.s42.dl.DLAttribute;
import de.s42.dl.DLEnum;
import de.s42.dl.DLInstance;
import de.s42.dl.DLModule;
import de.s42.dl.DLType;
import de.s42.dl.core.BaseDLCore;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.pragmas.DefinePragmaPragma;
import de.s42.log.LogManager;
import de.s42.log.Logger;
import java.util.Arrays;
import java.util.Optional;

/**
 * This example shows the possibilites of DL only. All types, enums, pragmas and instances are defined in DL (Except the
 * DefinePragmaPragma to allow definition of further pragmas).
 *
 * @author Benjamin Schiller
 */
public class Main
{

	protected final static String MODULE_PATH = "de/s42/dl/examples/dlonly/module.dl";

	private final static Logger log = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) throws Exception
	{
		log.info("Starting DL Only Example");

		// setup base dl core and allow definitions and require
		BaseDLCore core = new BaseDLCore(true);
		DefaultCore.loadResolvers(core);
		// this pragma is necessary to bootstrap new pragma definitions in DL
		core.definePragma(new DefinePragmaPragma());

		// load module
		DLModule module = core.parse(MODULE_PATH);

		StringBuilder builder = new StringBuilder("\n");

		// introspect enum types
		for (DLEnum enumType : core.getEnums()) {
			introspectEnum(enumType, builder);
		}

		// introspect complex types
		for (DLType type : core.getComplexTypes()) {
			introspectType(type, builder);
		}

		// introspect instances
		introspectInstance(module, builder);

		log.debug(builder.toString());

		// retrieve exported config
		DLInstance config = core.getExported("config").orElseThrow();

		log.debug("Config", config, config.get("debug"));
	}

	protected static void introspectEnum(DLEnum enumType, StringBuilder builder)
	{
		assert enumType != null;
		assert builder != null;

		builder
			.append("Enum ")
			.append(enumType.getCanonicalName());

		for (DLAnnotation annotation : enumType.getAnnotations()) {

			builder
				.append(" @")
				.append(annotation.getName())
				.append(Arrays.toString(annotation.getFlatParameters()));
		}

		builder
			.append(" ")
			.append(enumType.getValues())
			.append("\n");
	}

	protected static void introspectType(DLType type, StringBuilder builder)
	{
		assert type != null;
		assert builder != null;

		builder
			.append("Type ")
			.append(type.getCanonicalName())
			.append(" abstract:").append(type.isAbstract())
			.append(" final:").append(type.isFinal())
			.append(" complex:").append(type.isComplexType())
			.append(" dynamic:").append(type.isDynamic());

		for (DLAnnotation annotation : type.getAnnotations()) {

			builder
				.append(" @")
				.append(annotation.getName())
				.append(Arrays.toString(annotation.getFlatParameters()));
		}

		builder
			.append("\n");

		for (DLType parents : type.getParents()) {

			builder
				.append("  Parent ")
				.append(parents.getCanonicalName())
				.append("\n");
		}

		for (DLType contains : type.getContainedTypes()) {

			builder
				.append("  Contains ")
				.append(contains.getCanonicalName())
				.append("\n");
		}

		for (DLAttribute attribute : type.getAttributes()) {

			builder
				.append("  Attribute ")
				.append(attribute.getType().getCanonicalName())
				.append(" ")
				.append(attribute.getName())
				.append(" ")
				.append((Object) attribute.getDefaultValue());

			for (DLAnnotation annotation : attribute.getAnnotations()) {

				builder
					.append(" @")
					.append(annotation.getName())
					.append(Arrays.toString(annotation.getFlatParameters()));
			}

			builder
				.append("\n");
		}
	}

	protected static void introspectInstance(DLInstance instance, StringBuilder builder)
	{
		assert instance != null;
		assert builder != null;

		for (DLInstance child : instance.getChildren()) {

			builder
				.append("Instance ")
				.append(child.getType().getCanonicalName())
				.append(" ")
				.append(child.getName());

			for (DLAnnotation annotation : child.getAnnotations()) {

				builder
					.append(" @")
					.append(annotation.getName())
					.append(Arrays.toString(annotation.getFlatParameters()));
			}

			builder
				.append("\n");

			for (String attributeName : child.getAttributeNames()) {

				Optional<DLAttribute> optAttribute = child.getType().getAttribute(attributeName);

				// type backed attributes
				if (optAttribute.isPresent()) {

					DLAttribute attribute = optAttribute.orElseThrow();

					builder
						.append("  Attribute ")
						.append(attribute.getType().getCanonicalName())
						.append(" ")
						.append(attribute.getName())
						.append(" ")
						.append(valueToString(child.get(attribute.getName())))
						.append("\n");
				} // dynamic attributes
				else {

					builder
						.append("  Dynamic Attribute ")
						.append(attributeName)
						.append(" ")
						.append(valueToString(child.get(attributeName)))
						.append("\n");
				}
			}

			introspectInstance(child, builder);
		}
	}

	protected static String valueToString(Object value)
	{
		if (value == null) {
			return null;
		}

		// use conversionhelper if conversion available
		if (ConversionHelper.canConvert(value.getClass(), String.class)) {
			return ConversionHelper.convert(value, String.class);
		}

		// special handling for arrays
		if (value.getClass().isArray()) {
			return Arrays.toString((Object[]) value);
		}

		return value.toString();
	}
}
