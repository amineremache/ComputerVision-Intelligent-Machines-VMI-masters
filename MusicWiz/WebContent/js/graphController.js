'use strict';

angular.module('musicwizApp')
    .controller('graphController', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
        $scope.graph_items = ['Bar Chart', 'Pie Chart', 'Line Chart'];
        $scope.query_items = [];

        $scope.graph_list_url = "http://localhost:8080/MusicWiz/rest/Music/graphList";
        $scope.artist_list_url = 'http://localhost:8080/MusicWiz/rest/Music/artists';

        $scope.graph_labels = [];
        $scope.graph_data = [];

        $scope.endPoints = {
            'Top Artists': 'http://localhost:8080/MusicWiz/rest/Music/artists',
            'Top Countries': 'http://localhost:8080/MusicWiz/rest/Music/locations',
            'Top Labels': 'http://localhost:8080/MusicWiz/rest/Music/labels',
            'Top Releases': 'http://localhost:8080/MusicWiz/rest/Music/releases'
        };

        function queryGraphAPI(itemSelected, selectedQueryItem) {
            // window.alert(selectedQueryItem);
            $http.get(selectedQueryItem).then(function (response) {
                $scope.test_data = response.data;
                $scope.graph_labels = [];
                $scope.graph_data = [];

                for (var key in $scope.test_data) {
                    if ($scope.test_data.hasOwnProperty(key)) {
                        $scope.graph_labels.push(key);
                        $scope.graph_data.push($scope.test_data[key]);
                    }
                }
                $scope.barChartName = itemSelected;
                $scope.pieChartName = itemSelected;
                $scope.lineChartName = itemSelected;
            });
        }

        $scope.init = function () {
            $http.get($scope.graph_list_url)
                .then(function (response) {
                    $scope.query_items = response.data;
                    // });
                    console.log($scope.query_items[0]);
                    $scope.selectedQuery = $scope.query_items[0];
                    $scope.selectedGraph = $scope.graph_items[0];
                    // window.alert($scope.selectedQuery);
                    queryGraphAPI($scope.selectedQuery, $scope.endPoints[$scope.selectedQuery]);
                });
        };

        $scope.graphItemSelected = function (item) {
            $scope.selectedGraph = item;
        };

        $scope.queryItemSelected = function (item) {
            $scope.selectedQuery = item;
            queryGraphAPI(item, $scope.endPoints[item]);
        };

        $scope.hideCharts = false;
        $timeout($scope.init);
    }]);