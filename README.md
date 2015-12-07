<!---
license: Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

# cl.kunder.webview
This cordova plugin enables you to open a second webview in your app.
This webview is totally independent from the main webview, but allows you tu access plugins and other Cordova resources.

It's possible to modify this plugin to allow multiple webviews.

## How to use it

First, add the plugin to your cordova application

    cordova plugin add github.com/kunder-lab/cl.kunder.webview.git

To open a new webview, just call in your app's js:

    webview.Show(URL);

Where `URL` is the path to the page to be opened. In Android, the plugin automatically adds the prefix `file:///android_asset/www/`

Then, to close the second webview and return to the main view, call in your second webview (the opened webview, not the main webview):

    webview.Close();

This will close and destroy the second webview.
