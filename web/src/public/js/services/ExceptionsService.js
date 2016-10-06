/**
 * Created by stephanie.chou on 8/12/16.
 */
(function() {
    var app = angular.module('exceptionsService', []);

    app.service("ExceptionsService", function($http) {

        var service = {};

        service.nodeException = function () {
            return $http.get('/exception', {
                method: 'GET'
            });
        };

        service.javaException = function () {
            return $http.get('/java_error', {
                method: 'GET'
            });
        };

        service.dbException = function () {
            return $http.get('/sql_error', {
                method: 'GET'
            })
        };

        return service;
    });
}).call(this);

