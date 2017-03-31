/*
// voir http://www.webglearth.org/
 */

var Map = function() {
    var map;
    var line_points = [];
};

Map.prototype = {

    init: function () {

        this.map = L.map('map').setView([51.505, -0.09], 13);

        // add an OpenStreetMap tile layer
        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);
    },

    addPoint: function (pins) {

        // Create array of lat,lon points
        this.line_points = [];

        for (index in pins) {
            // add a marker in the given location, attach some popup content to it and open the popup
            var y = pins[index].point.y;
            var x = pins[index].point.x;
            // console.log("["+y+", "+x+"]");
            this.line_points.push([y, x]);
        }

        // Define polyline options
        // http://leafletjs.com/reference.html#polyline
        var polyline_options = {
            color: '#000'
        };

        // Defining a polygon here instead of a polyline will connect the
        // endpoints and fill the path.
        // http://leafletjs.com/reference.html#polygon
        L.polyline(this.line_points, polyline_options).addTo(this.map);

    },

    changePosition : function(lat,long) {
        this.map.panTo(new L.LatLng(lat, long));
    },

    setBounds: function(lat1, lon1, lat2, lon2) {
        var southWest = L.latLng(lat1, lon1);
        var northEast = L.latLng(lat2, lon2);
        var bounds = L.latLngBounds(southWest, northEast);

        this.map.fitBounds(bounds, {});
    }

}
