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
package de.s42.dl.examples.simpleconfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Benjamin Schiller
 */
public class Configuration
{
	protected String name;
	protected String login;
	protected UUID uuid;
	protected int id;
	protected double depth;
	protected boolean active;
	protected String[] tags;
	protected final List<Double> scores = new ArrayList<>();
	protected Map<String, Double> mapped = new HashMap<>();

	// <editor-fold desc="Getters/Setters" defaultstate="collapsed">
	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public double getDepth()
	{
		return depth;
	}

	public void setDepth(double depth)
	{
		this.depth = depth;
	}

	public String[] getTags()
	{
		return tags;
	}

	public void setTags(String[] tags)
	{
		this.tags = tags;
	}

	public List<Double> getScores()
	{
		return Collections.unmodifiableList(scores);
	}

	public void setScores(List<Double> scores)
	{
		this.scores.clear();
		this.scores.addAll(scores);
	}

	public Map<String, Double> getMapped()
	{
		return Collections.unmodifiableMap(mapped);
	}

	public void setMapped(Map<String, Double> mapped)
	{
		this.mapped.clear();
		this.mapped.putAll(mapped);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	// </editor-fold>	
}
