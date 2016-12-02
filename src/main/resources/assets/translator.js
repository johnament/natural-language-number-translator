angular.module('translatorApp', [])
    .controller('TranslatorController', function($http) {
        var translator = this;
        translator.toTranslate = "";
        translator.translated = "";
        translator.setTranslated = function(response) {
            translator.translated = response.data;
            translator.toTranslate = "";
        };
        translator.doTranslate = function() {
            $http
                .get('/numbers/en?number='+translator.toTranslate)
                .then(translator.setTranslated, translator.setTranslated);
        };
    });