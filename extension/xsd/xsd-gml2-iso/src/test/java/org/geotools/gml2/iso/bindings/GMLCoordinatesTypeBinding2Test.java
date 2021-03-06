/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.gml2.iso.bindings;

import org.geotools.gml2.iso.GML;
import org.geotools.xml.Binding;
import org.opengis.geometry.coordinate.PointArray;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
/**
 * 
 *
 * @source $URL$
 */
public class GMLCoordinatesTypeBinding2Test extends GMLTestSupport {
    public void testType() {
        assertEquals(PointArray.class, binding(GML.CoordinatesType).getType());
    }

    public void testExecutionMode() {
        assertEquals(Binding.OVERRIDE, binding(GML.CoordinatesType).getExecutionMode());
    }

    public void testParse() throws Exception {
        GML2MockData.coordinates(document, document);

        PointArray c = (PointArray) parse();

        //TODO
        /*
        assertEquals(new Coordinate(1, 2), c.getCoordinate(0));
        assertEquals(new Coordinate(3, 4), c.getCoordinate(1));
        */
    }

    public void testEncode() throws Exception {
        Document doc = encode(GML2MockData.coordinates(), GML.coordinates);

        Node s = doc.getDocumentElement().getFirstChild();
        assertEquals("1.0,2.0,3.0 3.0,4.0,5.0", doc.getDocumentElement().getFirstChild().getNodeValue());
    }
}
