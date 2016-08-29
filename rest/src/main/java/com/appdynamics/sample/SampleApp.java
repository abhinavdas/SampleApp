/*
 *  Copyright 2016 AppDynamics, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License.
 *
 *  A copy of the License is located at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.appdynamics.sample;

import com.appdynamics.sample.resource.ProductResource;
import com.appdynamics.sample.resource.RESTExceptions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Created by mark.prichard on 6/21/16.
 */

public class SampleApp extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new JpaPersistModule("product"),
                new JerseyServletModule() {
                    protected void configureServlets() {
                        bind(ProductResource.class);
                        bind(RESTExceptions.class);
                        
                        filter("/*").through(PersistFilter.class);
                        serve("/*").with(GuiceContainer.class);
                    }
                }
        );
    }
}
