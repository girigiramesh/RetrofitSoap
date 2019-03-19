/*
 * Copyright 2016. Alejandro Sánchez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.retrofitsoap.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by hymavathi.k on 12/18/2017.
 */
@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
        @Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
        @Namespace(prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope")
})
public class UsCittiesResponse {
    @Element(required = false, name = "Body")
    private String body;

    @Root(name = "Body", strict = false)
    @Element(name = "GetInfoByCityResponse", required = false)
    private String data;

    @Root(name = "GetInfoByCityResponse", strict = false)
    @Namespace(reference = "http://www.webserviceX.NET")
    @Element(name = "GetInfoByCityResult", required = false)
    private String info;

    @Root(name = "GetInfoByCityResult", strict = false)
    @ElementList(name = "NewDataSet", required = false)
    List<TableUsCities> elements;

    public List<TableUsCities> getElements() {
        return elements;
    }

    public void setElements(List<TableUsCities> elements) {
        this.elements = elements;
    }
}