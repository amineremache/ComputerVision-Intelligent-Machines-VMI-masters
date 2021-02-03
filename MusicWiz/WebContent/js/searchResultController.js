'use strict';

angular.module('musicwizApp', ['ui.grid', 'chart.js', 'ngAnimate', 'ngBusy'])
    .controller('searchResultController', ['$scope', '$http', function ($scope, $http) {
        $scope.myData = [];
        $scope.hideGrid = true;
        $scope.searchQuery = '';
        // $scope.totalRows = 0;

        $scope.val = 0;
        $scope.runQuery = function () {
            if ($scope.searchQuery != '') {
                if($scope.searchQuery.toLowerCase().indexOf('release') != -1)
                    $scope.queryString = 'http://localhost:8080/MusicWiz/rest/Music/textSearch?searchInput=' + $scope.searchQuery;
                else
                    $scope.queryString = 'http://localhost:8080/MusicWiz/rest/Music/search?searchInput=' + $scope.searchQuery;

                window.alert($scope.queryString);
                $http.get($scope.queryString)
                    .then(function (response) {
                        $scope.myData = response.data;
                        $scope.hideGrid = $scope.myData.length > 0 ? false : true;
                    });
            } else {
                window.alert('please enter the search query first.')
            }
        };

        $scope.fetchTotalRows = function () {
            $scope.totalRows = '';
            $http.get('http://localhost:8080/MusicWiz/rest/Music/count')
                .then(function (response) {
                    $scope.totalRows = response.data;
                });
        };
    }]);