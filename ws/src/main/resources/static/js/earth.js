
var Map = function() {
    var map;
    var line_points = [];
};

Map.prototype = {

    init: function () {

        var options = {atmosphere: true, sky: true, center: [0, 0], zoom: 1};
        this.earth = new WE.map('earth_div', options);
        WE.tileLayer('http://otile{s}.mqcdn.com/tiles/1.0.0/sat/{z}/{x}/{y}.jpg', {
            subdomains: '1234',
            attribution: 'Tiles Courtesy of MapQuest'
        }).addTo(this.earth);

    },

    addPoint: function (pins) {

        this.line_points = [];
        for (index in pins) {
            // add a marker in the given location, attach some popup content to it and open the popup
            var y = pins[index].y;
            var x = pins[index].x;
            console.log("["+y+", "+x+"]");
            this.line_points.push([y, x]);
        }

        var polygonA = WE.polygon(this.line_points);
        polygonA.addTo(this.earth);

    },

    changePosition : function(lat,long) {
        this.earth.panTo(new L.LatLng(lat, long));
    }
}
