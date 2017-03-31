var app = angular.module("bearingApp", []);

app.controller('homeCtrl', ['$scope', '$http', function($scope, $http) {

	$scope.departure = {};
	$scope.arrival 	 = {};
	$scope.route     = [];
	$scope.running	 = false;
	$scope.lastRoute = [];
	$scope.map = new Map();
	$scope.map.init();

    var isOldRoute   = false;

    var _checkRoute = function($scope) {
        return ($scope.departure.name && $scope.arrival.name)
    };

	var _addHistory = function () {

		if ($scope.lastRoute.length >= 5) {
			$scope.lastRoute.shift();
		}

		$scope.lastRoute.unshift({departure: $scope.departure, arrival : $scope.arrival});
	};

	$scope.getRoute = function () {
        isOldRoute = false;

        $scope.running = true;
		$scope.route = [];

		$http.get("/ws/route/get/"+$scope.departure.icao+"/"+$scope.arrival.icao)
			.success(function(data, status, headers, config) {

				$scope.route = data;
				$scope.map.addPoint(data);
				$scope.running = false;

				_addHistory();

				$scope.map.setBounds(
					$scope.departure.latitude, $scope.departure.longitude,
					$scope.arrival.latitude, $scope.arrival.longitude
				);

				getDistance();
			})
			.error(function(data, status, headers, config) {
				alert("Error get Route");
				$scope.running = false;
			});

	}

	var getDistance = function() {

		$http.get("/ws/route/distance/"+$scope.departure.icao+"/"+$scope.arrival.icao)
			.success(function(data, status, headers, config) {
				$scope.route.distance = data.distance;
			});

	}

	$scope.setRoute = function (depature, arrival) {
		isOldRoute = true;
        $scope.departure = depature;
		$scope.arrival 	 = arrival;
	};

    $scope.$watch('departure', function() {
        if($scope.departure.name) {
            var lat = $scope.departure.latitude;
            var lon = $scope.departure.longitude;
            $scope.map.changePosition(lat, lon);
        }

        if (isOldRoute == false) $scope.arrival = {};
		$scope.route = [];
    });

    $scope.$watch('arrival', function() {
        if (_checkRoute($scope)) $scope.getRoute();
    });

}]);

app.directive('searchAirport',['$http', function($http) {
	return {
		restrict: 'E',
		templateUrl: 'html/airportSearch.html',
		scope: {
			ad : "=airport",
            label: "=adLabel"
		},
		controller: function($scope) {

			$scope.searchAirport = function (value) {

				if (value.length >1) {
					$http.get("/ws/airport/search/"+value).success(function(data) {
						$scope.airports = data;
						$scope.activated = true;
					});
				}
			}

			$scope.setAirport = function(ad) {
				$scope.ad = ad;
				$scope.activated = false;
				$scope.code = "";
			}

		}
	}
}]);

app.directive('airportData', ['$http', function($http) {

	var _checkRoute = function($scope) {
		return ($scope.departure.name && $scope.arrival.name)
	};

	var _getDistance = function($scope) {
		var url = "/ws/airport/distanceAndBearing/icao/"+$scope.departure.icao+"/"+$scope.arrival.icao;
		$http.get(url).success(function(data) {
			$scope.distance = data.distance;
			$scope.bearing = data.bearing;
		});
	}

	return {
		restrict: 'E',
		templateUrl: 'html/airportData.html',
		scope: {
			departure : "=",
			arrival : "="
		},
		controller: function($scope) {

			$scope.distance = 0;
			$scope.bearing = 0;

            $scope.$watch('arrival', function() {
                if (_checkRoute($scope)) _getDistance($scope);
            });

		}
	}
}]);
