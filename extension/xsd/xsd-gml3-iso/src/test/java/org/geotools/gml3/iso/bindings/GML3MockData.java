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
package org.geotools.gml3.iso.bindings;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.geotools.feature.simple.ISOSimpleFeatureBuilder;
import org.geotools.feature.simple.ISOSimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.gml3.iso.GML;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.xml.XSD;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.Envelope;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.geometry.aggregate.MultiCurve;
import org.opengis.geometry.aggregate.MultiPoint;
import org.opengis.geometry.aggregate.MultiPrimitive;
import org.opengis.geometry.aggregate.MultiSurface;
import org.opengis.geometry.coordinate.PointArray;
import org.opengis.geometry.primitive.Curve;
import org.opengis.geometry.primitive.Point;
import org.opengis.geometry.primitive.Ring;
import org.opengis.geometry.primitive.Surface;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Utility class for creating test xml data for gml3 bindings.
 *
 * @author Justin Deoliveira, The Open Planning Project
 *
 *
 *
 *
 * @source $URL$
 */
public class GML3MockData {
    static XSD gml = GML.getInstance();
    
    static ISOGeometryBuilder gb = new ISOGeometryBuilder(DefaultGeographicCRS.WGS84);
    static ISOGeometryBuilder gb3D = new ISOGeometryBuilder(DefaultGeographicCRS.WGS84_3D);
    
    public static void setGML(XSD gml) {
        if (gml == null) {
            gml = GML.getInstance();
        }
        GML3MockData.gml = gml;
    }
    
    public static Element point(Document document, Node parent) {
        Element point = element(GML.Point, document, parent);
        point.setAttribute("srsName", "urn:x-ogc:def:crs:EPSG:6.11.2:4326");

        Element pos = element(qName("pos"), document, point);
        pos.appendChild(document.createTextNode("1.0 2.0 "));

        return point;
    }
    
    public static Element point3D(Document document, Node parent) {
        return point3D(document, parent, true);
    }
    
    public static Element point3D(Document document, Node parent, boolean addDimension) {
        Element point = element(GML.Point, document, parent);
        point.setAttribute("srsName", "urn:x-ogc:def:crs:EPSG:6.11.2:4326");
        if(addDimension) {
            point.setAttribute("srsDimension", "3");
        }

        Element pos = element(qName("pos"), document, point);
        pos.appendChild(document.createTextNode("1.0 2.0 10.0"));

        return point;
    }

    public static CoordinateReferenceSystem crs() {
        try {
            return CRS.decode("urn:x-ogc:def:crs:EPSG:6.11.2:4326");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Envelope bounds() {
        return new ReferencedEnvelope(0, 10, 0, 10, crs());
    }

    public static Point point() {
        Point p = gb.createPoint(gb.createDirectPosition(new double[] {1, 2}));
        //p.setUserData(crs());
        return p;
    }

    /**
     * Creates a Point using a LiteCoordinateSequence
     * with a 2D coordinate.
     * 
     * @return a 2D Point
     */
    public static Point point_3D() {
        return gb3D.createPoint(gb3D.createDirectPosition(new double[] {1, 2, 100}));
    }

    public static Curve lineString() {
    	PointArray pa = gb.createPointArray();
    	pa.add(gb.createDirectPosition(new double[]{1, 2}));
    	pa.add(gb.createDirectPosition(new double[]{3, 4}));
    	
        return gb.createCurve(pa);
    }
    /**
     * Creates a LineString using a LiteCoordinateSequence
     * with 2 3D coordinates.
     * 
     * @return a 3D LineString
     */
    public static Curve lineStringLite3D() {
    	PointArray pa = gb3D.createPointArray();
    	pa.add(gb3D.createDirectPosition(new double[]{1, 2, 100}));
    	pa.add(gb3D.createDirectPosition(new double[]{3, 4, 200}));
    	
        return gb.createCurve(pa);
    }

    public static Element lineString(Document document, Node parent) {
        return lineStringWithPos(document, parent);
    }
    
    public static Element lineStringProperty(Document document, Node parent) {
        Element property = element(qName("lineStringProperty"), document, parent);

        lineString(document, property);

        return property;
    }

    public static Element lineStringWithPos(Document document, Node parent) {
        Element lineString = element(qName("LineString"), document, parent);

        Element pos = element(qName("pos"), document, lineString);
        pos.appendChild(document.createTextNode("1.0 2.0"));

        pos = element(qName("pos"), document, lineString);
        pos.appendChild(document.createTextNode("3.0 4.0"));

        return lineString;
    }
    
    public static Element lineStringWithPos3D(Document document, Node parent) {
        Element lineString = element(qName("LineString"), document, parent);
        lineString.setAttribute("srsDimension", "3");

        Element pos = element(qName("pos"), document, lineString);
        pos.appendChild(document.createTextNode("1.0 2.0 10.0"));

        pos = element(qName("pos"), document, lineString);
        pos.appendChild(document.createTextNode("3.0 4.0 20.0"));
        
        return lineString;
    }

    public static Element lineStringWithPosList(Document document, Node parent) {
        Element lineString = element(qName("LineString"), document, parent);
        Element posList = element(qName("posList"), document, lineString);
        posList.appendChild(document.createTextNode("1.0 2.0 3.0 4.0"));

        return lineString;
    }
    
    public static Element lineStringWithPosList3D(Document document, Node parent) {
        return lineStringWithPosList3D(document, parent, true);
    }
    
    public static Element lineStringWithPosList3D(Document document, Node parent, boolean addSrsDimension) {
        Element lineString = element(qName("LineString"), document, parent);
        if(addSrsDimension) {
            lineString.setAttribute("srsDimension", "3");
        }
        Element posList = element(qName("posList"), document, lineString);
        posList.appendChild(document.createTextNode("1.0 2.0 10.0 3.0 4.0 20.0"));

        return lineString;
    }

    /*public static Element arcWithPosList(Document document, Node parent) {
        Element arc = element(qName("Arc"), document, parent);
        Element posList = element(qName("posList"), document, arc);
        posList.appendChild(document.createTextNode("1.0 1.0 2.0 2.0 3.0 1.0"));

        return arc;
    }

    public static Element arcStringWithPosList(Document document, Node parent) {
        Element arc = element(qName("ArcString"), document, parent);
        Element posList = element(qName("posList"), document, arc);
        posList.appendChild(document.createTextNode("1.0 1.0 2.0 2.0 3.0 1.0 5 5 7 3"));

        return arc;
    }

    public static Element circleWithPosList(Document document, Node parent) {
        Element circle = element(qName("Circle"), document, parent);
        Element posList = element(qName("posList"), document, circle);
        posList.appendChild(document.createTextNode("1.0 1.0 2.0 2.0 3.0 1.0"));

        return circle;
    }*/

    public static Ring linearRing() {
    	PointArray pa = gb.createPointArray();
    	pa.add(gb.createDirectPosition(new double[]{1, 1}));
    	pa.add(gb.createDirectPosition(new double[]{2, 2}));
    	pa.add(gb.createDirectPosition(new double[]{3, 1}));
    	pa.add(gb.createDirectPosition(new double[]{1, 1}));
    	
    	Curve c = gb.createCurve(pa);
    	Ring r = gb.createRing(Arrays.asList(c));
    	return r;
    }
    
    public static Ring linearRing3D() {
    	PointArray pa = gb3D.createPointArray();
    	pa.add(gb3D.createDirectPosition(new double[]{1.0, 2.0, 10.0}));
    	pa.add(gb3D.createDirectPosition(new double[]{3.0, 4.0, 20.0}));
    	pa.add(gb3D.createDirectPosition(new double[]{5.0, 2.0, 20.0}));
    	pa.add(gb3D.createDirectPosition(new double[]{1.0, 2.0, 10.0}));
    	
    	Curve c = gb3D.createCurve(pa);
    	Ring r = gb3D.createRing(Arrays.asList(c));
    	return r;
    }

    public static Element linearRing(Document document, Node parent) {
        return linearRingWithPos(document, parent);
    }

    public static Element linearRingWithPos(Document document, Node parent) {
        Element linearRing = element(qName("LinearRing"), document, parent);

        Element pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("1.0 2.0"));

        pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("3.0 4.0"));

        pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("5.0 2.0"));

        pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("1.0 2.0"));

        return linearRing;
    }
    
    public static Element linearRingWithPos3D(Document document, Node parent, boolean addSrsDimension) {
        Element linearRing = element(qName("LinearRing"), document, parent);
        if(addSrsDimension) {
            linearRing.setAttribute("srsDimension", "3");
        }

        Element pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("1.0 2.0 10.0"));

        pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("3.0 4.0 20.0"));

        pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("5.0 2.0 20.0"));

        pos = element(qName("pos"), document, linearRing);
        pos.appendChild(document.createTextNode("1.0 2.0 10.0"));

        return linearRing;
    }

    public static Element linearRingWithPosList(Document document, Node parent) {
        Element linearRing = element(qName("LinearRing"), document, parent);

        Element posList = element(qName("posList"), document, linearRing);

        linearRing.appendChild(posList);
        posList.appendChild(document.createTextNode("1.0 2.0 3.0 4.0 5.0 6.0 1.0 2.0"));

        return linearRing;
    }
    
    public static Element linearRingWithPosList3D(Document document, Node parent, boolean addSrsDimension) {
        Element linearRing = element(qName("LinearRing"), document, parent);
        if(addSrsDimension) {
            linearRing.setAttribute("srsDimension", "3");
        }

        Element posList = element(qName("posList"), document, linearRing);

        linearRing.appendChild(posList);
        posList.appendChild(document.createTextNode("1.0 2.0 10.0 3.0 4.0 20.0 5.0 6.0 30.0 1.0 2.0 10.0"));

        return linearRing;
    }

    public static Surface polygon() {
        /*return gf.createPolygon(linearRing(), null);*/
    	//TODO
    	return null;
    }

    /**
     * Creates a Polygon using a LiteCoordinateSequence
     * with 3D coordinates.
     * 
     * @return a 3D Polygon
     */
    public static Surface polygonLite3D() {
        /*return liteGF.createPolygon(
        		liteGF.createLinearRing(liteCSF.create(
        				new double[] { 1,1,100,  2,1,99, 2,2,98, 1,2,97, 1,1,100}, 3)),
        				null);*/
    	//TODO
    	return null;
    }

    public static Element polygon(Document document, Node parent) {
        return polygon(document,parent,qName("Polygon"),false); 
    }
    
    public static Element polygonWithPosList(Document document, Node parent) {
        return polygonWithPosList(document,parent,qName("Polygon"),false); 
    }
    
    public static Element polygonWithPosList3D(Document document, Node parent) {
        return polygonWithPosList3D(document,parent,qName("Polygon"),false); 
    }
    
    public static Element polygon(Document document, Node parent, QName name, boolean withInterior) {
        Element polygon = element(name, document, parent);

        Element exterior = element(qName("exterior"), document, polygon);
        linearRing(document, exterior);
        
        if ( withInterior ) {
            Element interior = element(qName("interior"), document, polygon);
            linearRing(document,interior);
        }

        return polygon;
    }
    
    public static Element polygon3D(Document document, Node parent, boolean addSrsDimension) {
        return polygonWithPos3D(document, parent, qName("Polygon"), true);
    }
    
    public static Element polygonWithPos3D(Document document, Node parent, QName name, boolean addSrsDimension) {
        Element polygon = element(name, document, parent);
        if(addSrsDimension) {
            polygon.setAttribute("srsDimension", "3");
        }

        Element exterior = element(qName("exterior"), document, polygon);
        linearRingWithPos3D(document, exterior, false);
        
        Element interior = element(qName("interior"), document, polygon);
        linearRingWithPos3D(document, interior, false);

        return polygon;
    }
    
    public static Element polygonWithPosList(Document document, Node parent, QName name, boolean withInterior) {
        Element polygon = element(name, document, parent);

        Element exterior = element(qName("exterior"), document, polygon);
        linearRingWithPosList(document, exterior);
        
        if ( withInterior ) {
            Element interior = element(qName("interior"), document, polygon);
            linearRingWithPosList(document,interior);
        }

        return polygon;
    }
    
    public static Element polygonWithPosList3D(Document document, Node parent, boolean addSrsDimension) {
        return polygonWithPosList3D(document, parent, qName("Polygon"), true);
    }
    
    public static Element polygonWithPosList3D(Document document, Node parent, QName name, boolean addSrsDimension) {
        Element polygon = element(name, document, parent);
        if(addSrsDimension) {
            polygon.setAttribute("srsDimension", "3");
        }

        Element exterior = element(qName("exterior"), document, polygon);
        linearRingWithPosList3D(document, exterior, false);
        
        Element interior = element(qName("interior"), document, polygon);
        linearRingWithPosList3D(document, interior, false);

        return polygon;
    }

    public static MultiPoint multiPoint() {
        /*return gf.createMultiPoint(new Coordinate[] { new Coordinate(1, 1), new Coordinate(2, 2) });*/
    	//TODO
    	return null;
    }

    public static Element multiPoint(Document document, Node parent) {
        Element multiPoint = element(qName("MultiPoint"), document, parent);

        // 2 pointMember elements
        Element pointMember = element(qName("pointMember"), document, multiPoint);
        point(document, pointMember);

        pointMember = element(qName("pointMember"), document, multiPoint);
        point(document, pointMember);

        //1 pointMembers elmenet with 2 members
        Element pointMembers = element(qName("pointMembers"), document, multiPoint);
        point(document, pointMembers);
        point(document, pointMembers);

        return multiPoint;
    }
    
    public static Element multiPoint3D(Document document, Node parent) {
        Element multiPoint = element(qName("MultiPoint"), document, parent);
        multiPoint.setAttribute("srsDimensions", "3");

        // 2 pointMember elements
        Element pointMember = element(qName("pointMember"), document, multiPoint);
        point3D(document, pointMember, false);

        pointMember = element(qName("pointMember"), document, multiPoint);
        point3D(document, pointMember, false);

        //1 pointMembers element with 2 members
        Element pointMembers = element(qName("pointMembers"), document, multiPoint);
        point3D(document, pointMembers, false);
        point3D(document, pointMembers, false);

        return multiPoint;
    }

    public static MultiCurve multiLineString() {
        //return gf.createMultiLineString(new LineString[] { lineString(), lineString() });
    	//TODO
    	return null;
    }

    //TODO : now we don't support curved geometry
    /*public static LineString compoundCurve() {
        CurvedGeometryFactory factory = new CurvedGeometryFactory(0.1);
        LineString curve = factory.createCurvedGeometry(new LiteCoordinateSequence(1, 1, 2, 2, 3,
                1, 5, 5, 7, 3));
        LineString straight = factory.createLineString(new LiteCoordinateSequence(7, 3, 10, 15));
        LineString compound = factory.createCurvedGeometry(curve, straight);
        return compound;
    }*/

    public static Element multiLineString(Document document, Node parent) {
        Element multiLineString = element(qName("MultiLineString"), document, parent);

        Element lineStringMember = element(qName("lineStringMember"), document, multiLineString);
        lineString(document, lineStringMember);

        lineStringMember = element(qName("lineStringMember"), document, multiLineString);
        lineString(document, lineStringMember);

        return multiLineString;
    }
    
    public static Element multiLineString3D(Document document, Node parent) {
        Element multiLineString = element(qName("MultiLineString"), document, parent);
        multiLineString.setAttribute("srsDimension", "3");

        Element lineStringMember = element(qName("lineStringMember"), document, multiLineString);
        lineStringWithPosList3D(document, lineStringMember, false);

        lineStringMember = element(qName("lineStringMember"), document, multiLineString);
        lineStringWithPosList3D(document, lineStringMember, false);

        return multiLineString;
    }

    public static Element multiCurve(Document document, Node parent) {
        return multiCurve(document, parent, true);
    }
    
    public static Element multiCurve(Document document, Node parent, boolean useCurveMember) {
        Element multiCurve = element(qName("MultiCurve"), document, parent);

        if (useCurveMember) {
            Element curveMember = element(qName("curveMember"), document, multiCurve);
            lineString(document, curveMember);
    
            curveMember = element(qName("curveMember"), document, multiCurve);
            lineString(document, curveMember);
        }
        else {
            Element curveMembers = element(qName("curveMembers"), document, multiCurve);
            lineString(document, curveMembers);
            lineString(document, curveMembers);
        }

        return multiCurve;
    }
    
    public static MultiSurface multiPolygon() {
        //return gf.createMultiPolygon(new Polygon[] { polygon(), polygon() });
    	//TODO
    	return null;
    }

    //TODO : now we don't support curved geometry
    /*public static Polygon curvePolygon() {
        LineString curve1 = gf.createCurvedGeometry(2, 0, 0, 2, 0, 2, 1, 2, 3, 4, 3);
        LineString line1 = gf.createLineString(new LiteCoordinateSequence(4, 3, 4, 5, 1, 4, 0, 0));
        LinearRing shell = (LinearRing) gf.createCurvedGeometry(Arrays.asList(curve1, line1));
        LinearRing hole = (LinearRing) gf.createCurvedGeometry(2, 1.7, 1, 1.4, 0.4, 1.6, 0.4,
                1.6, 0.5, 1.7, 1);
        return gf.createPolygon(shell, new LinearRing[] { hole });
    }*/

    public static Element multiPolygon(Document document, Node parent) {
        Element multiPolygon = element(qName("MultiPolygon"), document, parent);

        Element polygonMember = element(qName("polygonMember"), document, multiPolygon);
        polygon(document, polygonMember);

        polygonMember = element(qName("polygonMember"), document, multiPolygon);
        polygon(document, polygonMember);

        return multiPolygon;
    }
    
    public static Element multiPolygon3D(Document document, Node parent) {
        Element multiPolygon = element(qName("MultiPolygon"), document, parent);
        multiPolygon.setAttribute("srsDimension", "3");
        

        Element polygonMember = element(qName("polygonMember"), document, multiPolygon);
        polygon3D(document, polygonMember, false);

        polygonMember = element(qName("polygonMember"), document, multiPolygon);
        polygon3D(document, polygonMember, false);

        return multiPolygon;
    }

    
    public static Element multiSurface(Document document, Node parent) {
        return multiSurface(document, parent, true);
    }
        
    public static Element multiSurface(Document document, Node parent, boolean useSurfaceMember) {
        Element multiSurface = element(qName("MultiSurface"), document, parent);

        if (useSurfaceMember) {
            Element surfaceMember = element(qName("surfaceMember"), document, multiSurface);
            polygon(document, surfaceMember);
    
            surfaceMember = element(qName("surfaceMember"), document, multiSurface);
            polygon(document, surfaceMember);
        }
        else {
            Element surfaceMembers = element(qName("surfaceMembers"), document, multiSurface);
            polygon(document, surfaceMembers);
            polygon(document, surfaceMembers);
        }

        return multiSurface;
    }
    
    
    public static MultiPrimitive multiGeometry() {
        //return gf.createGeometryCollection(new Geometry[]{point(),lineString(),polygon()});
    	//TODO
    	return null;
    }
    
    public static Element multiGeometry(Document document, Node parent ) {
        Element multiGeometry = element(qName("MultiGeometry"), document, parent );
        
        Element geometryMember = element(qName("geometryMember"), document, multiGeometry);
        point(document,geometryMember);
        
        geometryMember = element(qName("geometryMember"), document, multiGeometry);
        lineString(document,geometryMember);
        
        geometryMember = element(qName("geometryMember"), document, multiGeometry);
        polygon(document,geometryMember);
        
        return multiGeometry;
    }

    public static Element surface(Document document, Node parent) {
        Element surface = element(qName("Surface"), document ,parent);
        Element patches = element(qName("patches"), document, surface);
        
        polygon(document,patches,qName("PolygonPatch"),true);
        
        return surface;
    }
    
    public static Element feature(Document document, Node parent) {
        Element feature = element(TEST.TestFeature, document, parent);
        Element geom = element(new QName(TEST.NAMESPACE, "geom"), document, feature);
        point(document, geom);

        Element count = GML3MockData.element(new QName(TEST.NAMESPACE, "count"), document, feature);
        count.appendChild(document.createTextNode("1"));

        return feature;
    }

    public static SimpleFeature feature() throws Exception {
        ISOSimpleFeatureTypeBuilder typeBuilder = new ISOSimpleFeatureTypeBuilder();
        typeBuilder.setName(TEST.TestFeature.getLocalPart());
        typeBuilder.setNamespaceURI(TEST.TestFeature.getNamespaceURI());

        typeBuilder.add("name", String.class);
        typeBuilder.add("description", String.class);
        typeBuilder.add("geom", Point.class);
        typeBuilder.add("count", Integer.class);
        typeBuilder.add("date", Date.class);

        SimpleFeatureType type = typeBuilder.buildFeatureType();

        ISOSimpleFeatureBuilder builder = new ISOSimpleFeatureBuilder(type);
        builder.add("theName");
        builder.add("theDescription");
        builder.add(point());
        builder.add(new Integer(1));
        builder.add(new Date());

        return builder.buildFeature("fid.1");
    }

    public static Element featureMember(Document document, Node parent) {
        Element featureMember = element(qName("featureMember"), document, parent);
        feature(document, featureMember);

        return featureMember;
    }

    public static Element element(QName name, Document document, Node parent) {
        Element element = document.createElementNS(name.getNamespaceURI(), name.getLocalPart());

        if (parent != null) {
            parent.appendChild(element);
        }

        return element;
    }
    
    public static QName qName(String local) {
        return gml.qName(local);
    }
}
