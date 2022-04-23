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

import de.s42.base.compile.InvalidCompilation;
import de.s42.dl.examples.gui.components.GuiWindow;
import de.s42.dl.core.DefaultCore;
import de.s42.dl.examples.gui.actions.DebugAction;
import de.s42.dl.examples.gui.components.Button;
import de.s42.dl.examples.gui.components.Component;
import de.s42.dl.examples.gui.components.Label;
import de.s42.dl.examples.gui.components.Panel;
import de.s42.dl.examples.gui.components.TextComponent;
import de.s42.dl.examples.gui.components.View;
import de.s42.dl.examples.gui.pragmas.LoadPluginClassPragma;
import de.s42.dl.exceptions.DLException;
import de.s42.dl.types.DefaultDLType;
import de.s42.dl.util.DLHelper;
import de.s42.dl.util.DLHelper.DLFileType;
import de.s42.log.LogManager;
import de.s42.log.Logger;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Benjamin Schiller
 */
public class GuiCore extends DefaultCore
{

	private final static Logger log = LogManager.getLogger(GuiCore.class.getName());

	protected final static Path BASE_PATH = Path.of("de/s42/dl/examples/gui/");
	protected final static Path L10N_PATH = BASE_PATH.resolve(Path.of("l10n_de.properties"));
	protected final static Path TYPES_PATH = BASE_PATH.resolve(Path.of("types.local.dlt"));
	protected final static Path PLUGINS_PATH = BASE_PATH.resolve(Path.of("plugins/"));

	public static GuiCore create(GuiWindow window) throws IOException, DLException, InvalidCompilation
	{
		assert window != null;

		GuiCore core = new GuiCore();
		core.init(window);
		return core;
	}

	private void init(GuiWindow window) throws IOException, DLException, InvalidCompilation
	{
		assert window != null;

		// Pragmas
		definePragma(new LoadPluginClassPragma());

		// Core types from Swing
		defineType(createType(Action.class));
		defineType(createType(AbstractAction.class));
		
		// Core types
		defineType(new RectangleDLType());
		defineType(createType(Font.class), "Font");
		
		// Component types
		defineType(createType(Component.class));
		defineType(createType(TextComponent.class));
		defineType(createType(Button.class), "Button");
		defineType(createType(Label.class), "Label");
		defineType(createType(Panel.class), "Panel");

		// Allow to access window
		defineType(createType(GuiWindow.class), "GuiWindow");
		addExported("window", window);

		// I18n and L10n
		DefaultDLType i18nType = new DefaultDLType(I18N.class.getName());
		i18nType.setAllowDynamicAttributes(true);
		defineType(i18nType);
		I18N i18n = new I18N(i18nType);
		i18n.init(L10N_PATH);
		addExported(i18n);

		// Implementation types
		defineType(createType(DebugAction.class), "DebugAction");
		
		// Load plugins
		Files.find(PLUGINS_PATH, 4, (file, attributes) -> {
			return file.getFileName().toString().equals("plugin.dl");
		}).forEach((file) -> {
			try {
				log.debug("Loading plugin", file.toAbsolutePath());
				parse(file.toString());
			} catch (DLException ex) {
				log.error(ex, "Error loading plugin", file.toAbsolutePath());
			}
		});

		// Print current core types as dl file
		DLHelper.writeTypesToFile(this, TYPES_PATH, DLFileType.HRF);
	}

	public View createView(Path viewFile)
	{
		assert viewFile != null;

		return new View(this, viewFile);
	}
}
