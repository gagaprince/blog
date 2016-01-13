$(document).ready(function(){
    var map =window.map = Map.createMap({});
    window.realMap = map.getRealMap();
    var centerMark = map.addMark();
    var points = [];
    var polyline = null;
    map.addMarkRightClickListener(centerMark,function(e,p){
       console.log(e);
       console.log(p);
    });
    map.addMarkDragStartListener(centerMark,function(e,p){
       console.log(e);
       console.log(p);
    });
    map.addMarkDragListener(centerMark,function(e,p){
       console.log(e);
       console.log(p);
    });
    map.addMapClickListener(function(e,p){
        console.log(e);
        console.log(p);
    });
    map.addMapRightClickListener(function(e,p){
        console.log(e);
        console.log(p);
        map.addMark(p);
        points.push(p);
        polyline = map.linePoints(points,{},polyline);
    });
//    map.addTipMark();
});