(function() {
  'use strict';

  angular
    .module('bearing')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
