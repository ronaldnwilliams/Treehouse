angular.module('demo', [])
  .controller('Hello', function($scope, $http) {
    $http.get('https://api.iextrading.com/1.0/stock/aapl/batch?types=quote')
      .then(function(response) {
        $scope.stock = response.data;
      });
  });
