/*******************************************************************************
 * Copyright (c) 2016 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tamas Miklossy (itemis AG) - initial implementation (bug #477980)
 *                                - Add support for polygon-based node shapes (bug #441352)
 *                                - modify grammar to allow empty attribute lists (bug #461506)		
 *
 *******************************************************************************/
package org.eclipse.gef4.dot.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.gef4.dot.internal.DotFileUtils;
import org.eclipse.gef4.dot.internal.parser.DotInjectorProvider;
import org.eclipse.gef4.dot.internal.parser.dot.DotAst;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(DotInjectorProvider.class)
public class DotParserTests {

	@Inject
	private ParseHelper<DotAst> parserHelper;

	@Inject
	private ValidationTestHelper validationTestHelper;

	@Test
	public void testEmptyString() {
		try {
			DotAst dotAst = parserHelper.parse("");
			assertNull(dotAst);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testEmptyGraph() {
		testString("graph {}");
	}

	@Test
	public void testEmptyDirectedGraph() {
		testString("digraph {}");
	}

	@Test
	public void testEmptyStrictGraph() {
		testString("strict graph {}");
	}

	@Test
	public void testEmptyStrictDirectedGraph() {
		testString("strict digraph {}");
	}

	@Test
	public void testGraphWithOneNode() {
		testString("graph {n1}");
	}

	@Test
	public void testGraphWithOneNodeAndEmptyNodeAttributeList() {
		testString("graph {n1[]}");
	}

	@Test
	public void testGraphWithOneEdge() {
		testString("graph {n1--n2}");
	}

	@Test
	public void testGraphWithOneEdgeAndEmptyEdgeAttributeList() {
		testString("graph {n1--n2[]}");
	}

	@Test
	public void testGraphWithEmptyGraphAttributeStatement() {
		testString("graph {graph[]}");
	}

	@Test
	public void testGraphWithEmptyNodeAttributeStatement() {
		testString("graph {node[]}");
	}

	@Test
	public void testGraphWithEmptyEdgeAttributeStatement() {
		testString("graph {edge[]}");
	}

	@Test
	public void testArrowShapesSingle() {
		testFile("arrowshapes_single.dot");
	}

	@Test
	public void testArrowShapesMultiple() {
		testFile("arrowshapes_multiple.dot");
	}

	@Test
	public void testArrowShapesDeprecated() {
		testFile("arrowshapes_deprecated.dot");
	}

	@Test
	public void testArrowshapesDirectionBoth() {
		testFile("arrowshapes_direction_both.dot");
	}

	@Test
	public void testArrowShapesInvalidModifiers() {
		testFile("arrowshapes_invalid_modifiers.dot");
	}

	@Test
	public void testPolygonBasedNodeShapes() {
		testFile("nodeshapes_polygon_based.dot");
	}

	private void testFile(String fileName) {
		String fileContents = DotFileUtils
				.read(new File(DotTestUtils.RESOURCES_TESTS + fileName));
		testString(fileContents);
	}

	private void testString(String text) {
		try {
			DotAst dotAst = parserHelper.parse(text);
			assertNotNull(dotAst);
			validationTestHelper.assertNoErrors(dotAst);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
