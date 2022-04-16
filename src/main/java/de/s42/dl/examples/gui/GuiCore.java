// <editor-fold desc="The MIT License" defaultstate="collapsed">
/*
 * The MIT License
 * 
 * Copyright 2022 Studio 42 GmbH (https://www.s42m.de).
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
package de.s42.dl.examples.gui;

import de.s42.dl.DLType;
import de.s42.dl.core.CoreDLInstance;
import de.s42.dl.examples.gui.components.GuiWindow;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.examples.gui.actions.DebugAction;
import de.s42.dl.examples.gui.components.Button;
import de.s42.dl.examples.gui.components.Component;
import de.s42.dl.examples.gui.components.Label;
import de.s42.dl.examples.gui.components.Panel;
import de.s42.dl.examples.gui.components.TextComponent;
import de.s42.dl.examples.gui.components.View;
import de.s42.dl.exceptions.DLException;
import de.s42.dl.instances.SimpleTypeDLInstance;
import de.s42.dl.types.CoreDLType;
import de.s42.dl.types.DefaultDLType;
import de.s42.dl.util.DLHelper;
import de.s42.dl.util.DLHelper.DLFileType;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Benjamin Schiller
 */
public class GuiCore extends DefaultCore
{

	protected final static Path BASE_PATH = Path.of("de/s42/dl/examples/gui/");
	protected final static Path L10N_PATH = Path.of("l10n_de.properties");
	protected final static Path TYPES_PATH = Path.of("types.local.dlt");

	public static GuiCore create(GuiWindow window) throws IOException, DLException
	{
		assert window != null;

		GuiCore core = new GuiCore();
		core.init(window);
		return core;
	}

	private void init(GuiWindow window) throws IOException, DLException
	{
		assert window != null;

		// core types
		CoreDLType coreType = (CoreDLType) getType(CoreDLType.DEFAULT_SYMBOL).get();
		addExported(new CoreDLInstance(this, coreType));
		defineType(new RectangleDLType());
		defineTypeFromClass(List.class);
		//defineTypeFromClass(Object[].class);
		//defineTypeFromClass(PropertyChangeListener[].class);
		defineTypeFromClass(Action.class);
		defineTypeFromClass(AbstractAction.class);
		defineTypeFromClass(Component.class);
		defineTypeFromClass(TextComponent.class);
		defineTypeFromClass(Button.class, "Button");
		defineTypeFromClass(Label.class, "Label");
		defineTypeFromClass(Panel.class, "Panel");

		// allow to access window
		DLType windowType = defineTypeFromClass(GuiWindow.class, "GuiWindow");
		addExported(new SimpleTypeDLInstance<>(window, windowType, "window"));

		// i18n and l10n
		DefaultDLType i18nType = new DefaultDLType(I18N.class.getName());
		i18nType.setAllowDynamicAttributes(true);
		defineType(i18nType);
		I18N i18n = new I18N(i18nType);
		i18n.init(BASE_PATH.resolve(L10N_PATH));
		addExported(i18n);

		// implementation types
		defineTypeFromClass(DebugAction.class, "DebugAction");

		// print current core types as dl file
		DLHelper.writeTypesToFile(this, BASE_PATH.resolve(TYPES_PATH), DLFileType.HRF);

		/* @test is the types file readable
		DefaultCore tCore = new DefaultCore();
		tCore.defineType(i18nType);
		tCore.defineType(new RectangleDLType());
		tCore.parseFile(BASE_PATH.resolve(TYPES_PATH));
		 */
	}

	public View createView(Path viewFile)
	{
		assert viewFile != null;

		return new View(this, viewFile);
	}
}
