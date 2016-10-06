(function() {
    var app = angular.module('businessTransactionController', []);

    app.controller('businessTransactionController', function ($scope, BusinessTransactionService) {

        $scope.products = [];

        $scope.newProduct = {
            newName: "",
            newStock: 0
        };

        $scope.selectedProduct = null;

        $scope.init = function() {
            $scope.getProducts().then(function () {
                // if there are no products then prepopulate with set things
                if (this.products.length < 1) {
                    this.setDefaultProducts();
                }
            }.bind(this));
        };

        $scope.setDefaultProducts = function () {
            var products = ["oranges",
                "apples",
                "lemons",
                "mangos"];

            var productCount = 100;

            for (var i = 0; i < products.length; i++) {
                this.newProduct = {
                    newName: products[i],
                    newStock: productCount
                };

                this.addNew();
            }

            this.getProducts();
        };

        var setupProductUpdate = function(product) {

            $scope.selectedProduct = product;
            $scope.selectedProduct.loading = false;
            $scope.selectedProduct.stock = parseInt(product.stock, 10);

            $scope.selectedProduct.save = function (decrement) {
                var useStock = decrement ? this.stock - 1 : this.stock + 1;

                BusinessTransactionService.save(this.id, this.name, useStock < 0 ? 0 : useStock).success(function (returnProduct) {
                    this.stock = parseInt(returnProduct.stock, 10);
                }.bind(this)).error(function () {
                    alert('Unable to update the product.');
                    return this.loading = false;
                });
            };

            $scope.selectedProduct.delete = function () {
                BusinessTransactionService.delete(this.id).success(function () {
                    var lookup, results;
                    this.loading = false;
                    results = [];
                    for (lookup in $scope.products) {
                        if (!$scope.products.hasOwnProperty(lookup)) {
                            continue;
                        }
                        if ($scope.products[lookup].id === product.id) {
                            $scope.products.splice(lookup, 1);
                            break;
                        } else {
                            results.push(void 0);
                        }
                    }
                    return results;
                }).error(function () {
                    alert('Unable to delete the product.');
                    return product.loading = false;
                });
            };

            return $scope.products.push(product);
        };

        $scope.getProducts = function () {
            $scope.products = [];
            return BusinessTransactionService.getProducts().success(function (data) {
                var product;
                for (product in data) {
                    if (!data.hasOwnProperty(product)) {
                        continue;
                    }
                    setupProductUpdate(data[product]);
                }

                return null;
            });
        };

        $scope.addNew = function () {

            $scope.loadingNew = true;
            
            BusinessTransactionService.add($scope.newProduct.newName, $scope.newProduct.newStock).success(function (data) {
                setupProductUpdate(data);
            }).error(function () {
                alert('Unable to add new product.');
                return $scope.loadingNew = false;
            }).finally(function(){
                $scope.loadingNew = false;
                $scope.newProduct.newName = "";
                $scope.newProduct.newStock = 0;
            });
        };

        $scope.reset = function () {
            for(var i = 0; i < this.products.length; i++){
                this.products[i].stock = 100;
                this.products[i].save(false);
            }
        };

        $scope.init();

    });
}).call(this);