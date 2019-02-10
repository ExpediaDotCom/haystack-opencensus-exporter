/*
 *  Copyright 2018 Expedia, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.expedia.www.opencensus.exporter.trace

import com.expedia.www.opencensus.exporter.trace.config.{GrpcAgentDispatcherConfig, HttpDispatcherConfig}
import org.scalatest.{FunSpec, GivenWhenThen, Matchers}

class HaystackTraceExporterSpec extends FunSpec with GivenWhenThen with Matchers {
  describe("Haystack trace exporter") {
    it ("should create and register with grpc client and throw illegal state exception if recreated") {
      HaystackTraceExporter.createAndRegister(new GrpcAgentDispatcherConfig("haystack-agent", 35000), "my-service")
      HaystackTraceExporter.handler should not be null

      intercept[IllegalStateException] {
        HaystackTraceExporter.createAndRegister(new GrpcAgentDispatcherConfig("haystack-agent", 35000), "my-service")
      }

      HaystackTraceExporter.unregister()
      HaystackTraceExporter.handler shouldBe null

      intercept[IllegalStateException] {
        HaystackTraceExporter.unregister()
      }
    }

    it ("should create and register with http client and throw illegal state exception if recreated") {
      HaystackTraceExporter.createAndRegister(new HttpDispatcherConfig("http://haystack-http"), "my-service")
      HaystackTraceExporter.handler should not be null

      intercept[IllegalStateException] {
        HaystackTraceExporter.createAndRegister(new HttpDispatcherConfig("haystack-agent"), "my-service")
      }

      HaystackTraceExporter.unregister()
      HaystackTraceExporter.handler shouldBe null

      intercept[IllegalStateException] {
        HaystackTraceExporter.unregister()
      }
    }
  }
}