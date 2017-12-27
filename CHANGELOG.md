# CHANGELOG

## 2.6.0

### Description
- New feature close webview with params
- Fixes to readme

#### Feature close with params
Added params for `Close` method. The second webview can send parameters to the main webview on close.
Usage: 
```js
// Second webview
webview.Close(success, error, params)
``` 

To receive this parameters, use `webview.SubscribeCallback(success, error)`. Parameters will be passed along the `success` callback.

```js
// Main webview
// This will be fired on second webview close
webview.SubscribeCallback(function(params) { console.log(params); }, function(){ console.log('error'); })
``` 

### Breaking Changes
No breaking changes on this release.

### Changelog
863478d25bf269b87792e1a94c3dfaa401daec26 - version bump 2.6.0
aad2154111edb48c9ab6bb39b8f543df065c6073 - updated readme
50e4d9a2546a1e26c6eae16fdf86ca94101651bd - readme updated
2576dba679752a853ed89687eb8bfbbe3953024b - moving the new parameter of hide to the end to avoid making a breaking change
8c4b0d612980be4230a160b90bf5143d99bb7a02 - changed the header of the readme
f272b3dcb898cee62712d50b40753f867272390d - added new functionality to documentation
014425c21d7ed5d4aa1caf47703db19981d3bdc6 - corrected a parameter that was not necessarely static
0e03fe1e0e1879e50a1773d0b1ff91d4c00a72c1 - Added support for sending params when webview is closed
83ffc54fc5dea1c882cb4681577d25a0c106bf04 - Update README.md
9a8595d583e3603b2eb2a96df84c7754c47b4509 - Fixes spelling mistake.

## 2.5.1

### Description
Add pluginInitialize method to adjust iOS 11 webview using the status bar space.

### Breaking Changes
No breaking changes on this release.

### Changelog
56abb96a8a195c97e34336a10ee5624d89a452f3 - Se añade función pluginInitialize para corregir problemas de comportamiento al abrir una nueva webview.

## 2.5.0

### Description
Add a method to adjust iOS 11 webview using the status bar space. This is a temporary fix for iOS 11 grey status bar bug.
This change does not fix iPhone X spaces.

### Breaking Changes
No breaking changes on this release.

### Changelog
08467a8f93f376c75999a6cf4e7957f6c69e6d4e - Se añade función para ajustar el comportamiento de la vista en iOS 11
4daeeee456b461b872d7c252a6f3503cc7c4e836 - Se agrega método para deshabilitar statusbar en ios11

## 2.4.2

### Description
NPM standar fixes.

### Breaking Changes
No breaking changes on this release.

### Changelog
b49215591e38831751789f0e1b5d671e806735a4 - NPM name and IDs replaced by kunder-cordova-plugin-webview
ba27b3b52ae5ae5aacb9fd6105bdb62c23d422ba - cambios requeridos
67f828ce7994dde7af7242fab46de66c8827b67b - cambio de nombre siguiendo las notaciones actuales
d967dadfaf7984529c9d4960d0740adf6cfdfee7 - se agrega version 2.4.1
484d5b4fe546caa5981275da90c9542d0d6436c1 - corrección version plugin
7deb9319c2680eaca952f4707b0fe84b54b9bbfb - modificación de id y name en package.json
46e51332cf969de69e2f46f3d070538e08d70de4 - se modifica id del plugin
7c9a47e73f07daae38e5fa12095d9ffd43fe513f - modificación variable "name" package.json

## 2.4.1

### Description
Cordova 7 compatibility fixes.

### Breaking Changes
No breaking changes on this release.

### Changelog
df29697a207c62afaf0fb0010432a6dde09e1c25 - corrección nombre del package
4071f065c325f68dc2fb535fda3a908e1a2efc77 - corrección nombre plugin

## 2.4.0

### Description
Added compatibility for Cordova 7.

### New Features
Added support for Cordova's browser platform 7.
Added close callback for Android and iOS.
Added loading when blank page is shown.
Added HideLoading function to close the loading in ionic afterEnter event.

### Breaking Changes
No breaking changes on this release.

### Changelog
e604714ab7df111238267e047dab69636a465baf - se agrega archivo package.json
19b653596339337f985bbbf78114e2ebfab8104b - Fix Show method parameters
f646da2355cc334d5891a20c5a083229a82a7d57 - Readme updated
705a54ba288640087e3da7f74bd6b538ef480d7c - Including loading when blank page is shown. Adding HideLoading function to close the loading in ionic afterEnter event.
bdcd58c46f5d97f76f1633da618754f1fe48a2d5 - Moving finish to after success callback called (reverted from commit 458a0274de602d92590eeaacae6d44001762820b)
88c26a94a4da8a440cc23208c5d381a13e3b6eaa - Moving finish to after success callback called (reverted from commit 458a0274de602d92590eeaacae6d44001762820b)
458a0274de602d92590eeaacae6d44001762820b - Moving finish to after success callback called
75121007c2be162fe3da3a010031d8a1aece1cd4 - Adding documentation about SubscribeExitCallback and ExitApp methods
9f38a834e45a1645300c14606b99fc4aec446fc3 - Se añade una funcionalidad que permite cerrar la aplicación en iOS de forma directa y de forma indirecta en Android. Para Android, se requiere registrar un callback y un resume para cerrar la aplicación por medio de ionic.Platform.exitApp()

## 2.3.0

### Description
Added support for Cordova's browser platform.

### New Features
Added support for browser platform.

### Breaking Changes
No breaking changes on this release.

### Changelog
4857d2291ce95f1accf1ad4bc8a04cd7577ccb64 - Version bump 2.3.0
cfc341eaffae216b35c9c2e2d6063f48cb96ae05 - agregado soporte a browser
3b0f0fe8b84fff8477b2f540891f483f04dc14b9 - fixed android prefix documentation