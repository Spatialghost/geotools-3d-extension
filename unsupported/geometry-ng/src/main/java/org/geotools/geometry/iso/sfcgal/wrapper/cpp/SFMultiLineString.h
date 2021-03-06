/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2015, Open Source Geospatial Foundation (OSGeo)
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

/**
 * @author Donguk Seo
 *
 */

#ifndef JAVACPP_SFCGAL_MultiLineString_H
#define JAVACPP_SFCGAL_MultiLineString_H

#include <SFCGAL/MultiLineString.h>
#include "SFGeometryCollection.h"
#include "SFLineString.h"

class SFMultiLineString : public SFGeometryCollection {
public:	
	SFMultiLineString() : SFGeometryCollection(new SFCGAL::MultiLineString()) { }
	//SFMultiLineString(const SFMultiLineString& other) : SFGeometry(other.data) { }
	SFMultiLineString(const SFCGAL::MultiLineString& other) : SFGeometryCollection(new SFCGAL::MultiLineString(other)) { }
	//SFMultiLineString(SFCGAL::MultiLineString& other) : SFGeometry(new SFCGAL::MultiLineString(other)) { }
	SFMultiLineString(SFCGAL::MultiLineString* other) : SFGeometryCollection(other) { }

	SFMultiLineString& operator=(const SFMultiLineString& other) {
		data = other.data;
		
		return *this;
	}
	
	~SFMultiLineString() { }
	
	
	//--SFCGAL::Geometry
	SFMultiLineString* clone() const {
		return new SFMultiLineString(*this);
	}
	
	std::string geometryType() const {
		return data->geometryType();
	}	
	
	int geometryTypeId() const {
		return data->geometryTypeId();
	}
	

	const SFLineString& lineStringN( size_t const & n) const {
		return *(new SFLineString(((SFCGAL::MultiLineString *)data)->lineStringN(n)));
	}
	
	SFLineString& lineStringN(size_t const& n) {
		return *(new SFLineString(((SFCGAL::MultiLineString *)data)->lineStringN(n)));
	}
	
};

#endif
