/* global malarkey:false, toastr:false, moment:false */
(function() {
  'use strict';

  angular
    .module('bearing')
    .constant('toastr', toastr)
    .constant('moment', moment);

})();
