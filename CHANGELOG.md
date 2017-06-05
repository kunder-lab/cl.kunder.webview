# CHANGELOG

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
