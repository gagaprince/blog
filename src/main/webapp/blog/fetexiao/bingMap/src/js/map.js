(function(window){
    var map = null;
    var key = 'An7mI-ncdAXO6T4Pzie8SwYcyKDFmwA-t-EHHEMpU9TmsyFKXXAGVWo4ITNfhdUI';

//    var boundingBox = Microsoft.Maps.LocationRect.fromLocations(new Microsoft.Maps.Location(47.618594, -122.347618), new Microsoft.Maps.Location(47.620700, -122.347584), new Microsoft.Maps.Location(47.622052, -122.345869));
//    map = new Microsoft.Maps.Map(document.getElementById('SDKmap'), {credentials: 'Your Bing Maps Key', bounds: boundingBox});
    //这里三个点不知道是哪三个点


    function initMap(){
        var initObj = {
            credentials:key,//map key  http://www.bingmapsportal.com/ 可以申请
            enableClickableLogo:false,//不加logo
            enableSearchLogo:false,      //搜索框
            showDashboard:true,    //控制栏
            showMapTypeSelector:true,  // 当控制栏true时 是否显示autoMatic选项
            showScalebar:false,         //当控制栏true时，是否显示放大缩小控件
//        backgroundColor : new Microsoft.Maps.Color(Math.round(255*Math.random()),Math.round(255*Math.random()),Math.round(255*Math.random()),Math.round(255*Math.random())),
//        disablePanning:true,        //不知道干嘛的
//        bounds:boundingBox,         //viewOptions
            center: new Microsoft.Maps.Location(47.609771, -122.2321543125),//中心点坐标 和
            zoom: 8, // 放缩比
//            mapTypeId: Microsoft.Maps.MapTypeId.aerial,
//            mapTypeId: Microsoft.Maps.MapTypeId.birdseye,
            width:500,
            height:500
        }


        map = new Microsoft.Maps.Map(document.getElementById('myMap'), initObj);

        console.log(map.getImageryId());
        console.log(map.getHeading());
        console.log(map.getZoom());
        console.log(map.getWidth()+"/"+map.getHeight());
        console.log("map.getViewport:"+map.getViewportX()+"/"+map.getViewportY());
        console.log("map.getMetersPerPixel:"+map.getMetersPerPixel());
        console.log("map.getBounds:"+map.getBounds());
        console.log("map.isRotationEnabled:"+map.isRotationEnabled());
        console.log("map.isMercator:"+map.isMercator());
        console.log("map.getZoomRange:"+map.getZoomRange().min+"-"+map.getZoomRange().max);
    }
    $(document).ready(function(){
        initMap();
    });
})(window);