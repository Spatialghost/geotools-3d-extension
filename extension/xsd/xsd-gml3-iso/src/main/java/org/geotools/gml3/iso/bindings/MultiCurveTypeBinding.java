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

import java.util.List;

import javax.xml.namespace.QName;

import org.geotools.gml3.iso.GML;
import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.geometry.ISOGeometryBuilder;
import org.opengis.geometry.aggregate.MultiCurve;
import org.opengis.geometry.primitive.Curve;


/**
 * Binding object for the type http://www.opengis.net/gml:MultiCurveType.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;complexType name="MultiCurveType"&gt;
 *      &lt;annotation&gt;
 *          &lt;documentation&gt;A MultiCurve is defined by one or more Curves, referenced through curveMember elements.&lt;/documentation&gt;
 *      &lt;/annotation&gt;
 *      &lt;complexContent&gt;
 *          &lt;extension base="gml:AbstractGeometricAggregateType"&gt;
 *              &lt;sequence&gt;
 *                  &lt;annotation&gt;
 *                      &lt;documentation&gt;The members of the geometric aggregate can be specified either using the "standard" property or the array property style. It is also valid to use both the "standard" and the array property style in the same collection.
 *  NOTE: Array properties cannot reference remote geometry elements.&lt;/documentation&gt;
 *                  &lt;/annotation&gt;
 *                  &lt;element maxOccurs="unbounded" minOccurs="0" ref="gml:curveMember"/&gt;
 *                  &lt;element minOccurs="0" ref="gml:curveMembers"/&gt;
 *              &lt;/sequence&gt;
 *          &lt;/extension&gt;
 *      &lt;/complexContent&gt;
 *  &lt;/complexType&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 *
 *
 *
 * @source $URL$
 */
public class MultiCurveTypeBinding extends AbstractComplexBinding {
    protected ISOGeometryBuilder gb;

    public MultiCurveTypeBinding(ISOGeometryBuilder gb) {
        this.gb = gb;
    }

    /**
     * @generated
     */
    public QName getTarget() {
        return GML.MultiCurveType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return MultiCurve.class;
    }

    public int getExecutionMode() {
        return BEFORE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        //&lt;element maxOccurs="unbounded" minOccurs="0" ref="gml:curveMember"/&gt;
        List curves = node.getChildValues(Curve.class);

        //&lt;element minOccurs="0" ref="gml:curveMembers"/&gt;
        if (node.hasChild(MultiCurve[].class)) {
            //this is a hack but we map curve itself to multi line string
        	MultiCurve[] lines = (MultiCurve[]) node.getChildValue(MultiCurve[].class);
            for (MultiCurve mline : lines) {
                /*if (mline.getNumGeometries() == 1) {
                    curves.add(mline.getGeometryN(0));
                }
                else {
                    //TODO: perhaps log this instead?
                    throw new IllegalArgumentException("Unable to handle curve member with multiple segments");
                }*/
            }
        }
        
        //return gf.createMultiLineString((LineString[]) curves.toArray(new LineString[curves.size()]));
        return null;
    }

    public Object getProperty(Object object, QName name) throws Exception {
        if ("curveMember".equals(name.getLocalPart())) {
            /*MultiLineString multiCurve = (MultiLineString) object;
            LineString[] members = new LineString[multiCurve.getNumGeometries()];

            for (int i = 0; i < members.length; i++) {
                members[i] = (LineString) multiCurve.getGeometryN(i);
            }

            GML3EncodingUtils.setChildIDs(multiCurve);*/

            return null;
        } else {
            super.getProperty(object, name);
        }

        return null;
    }
}