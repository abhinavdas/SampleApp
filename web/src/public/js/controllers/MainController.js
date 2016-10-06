/**
 * Created by stephanie.chou on 8/12/16.
 */

(function() {
    var app = angular.module('mainController', []);

    app.controller('mainController', function(
            $scope,
            $http,
            $location,
            CONTROLLER_URL,
            CONTROLLER_PORT,
            CONTROLLER_SSL,
            CONTROLLER_ACCOUNT_NAME,
            APP_ID) {

            $scope.ready = false;

            $scope.init = function () {
                $scope.ready = true;
            };

            $scope.navigateTo = function (path) {
                $location.path("/" + path);
            };

            $scope.openController = function (key) {
                var location = '';

                switch (key) {
                    case "Business Transaction List":
                        location = "APP_BT_LIST";
                        break;
                    case "Tiers":
                        location = "APP_INFRASTRUCTURE";
                        break;
                    case "Flow Map":
                        location = "APP_DASHBOARD";
                        break;
                    // case "Slow Response Times":
                    //     location = "APP_SLOW_RESPONSE_TIMES";
                    //     break;
                    case "Exceptions":
                        location = "APP_ERRORS";
                        break;
                    default:
                        location = "AD_HOME_OVERVIEW";
                        break;
                }

                var s = CONTROLLER_SSL == "true" ? "s" : "" ;
                var url = "http" + s + "://" + CONTROLLER_URL + ":" + CONTROLLER_PORT + "/?accountName="+ CONTROLLER_ACCOUNT_NAME +"#/location=" + location + "&application=" + APP_ID;
                window.open(url, "AppDynamicsController");
            };
        
            $scope.init();
        }
    );
}).call(this);
