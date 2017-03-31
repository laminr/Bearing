(function() {

    angular
        .module('bearing.widget')
        .directive('searchAirport', searchAirport);

    /**@ngIngect*/
    function searchAirport($http) {

        var directives = {
            restrict: 'E',
                templateUrl: 'html/airportSearch.html',
            scope: {
                ad : "=airport",
                label: "=adLabel"
            },
            controller: searchAirportCtrl
        };

        return directives;

        /** @ngInject */
        function searchAirportCtrl($http) {
            var dir = this;

            dir.searchAirport = function (value) {

                if (value.length >1) {
                    $http.get("http://localhost:5000/ws/airport/search/"+value).success(function(data) {
                        dir.airports = data;
                        dir.activated = true;
                    });
                }
            }

            dir.setAirport = function(ad) {
                dir.ad = ad;
                dir.activated = false;
                dir.code = "";
            }
        }
    }

})();