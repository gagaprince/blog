(function(window){
    var map = null;
    var key = 'An7mI-ncdAXO6T4Pzie8SwYcyKDFmwA-t-EHHEMpU9TmsyFKXXAGVWo4ITNfhdUI';

//    var boundingBox = Microsoft.Maps.LocationRect.fromLocations(new Microsoft.Maps.Location(47.618594, -122.347618), new Microsoft.Maps.Location(47.620700, -122.347584), new Microsoft.Maps.Location(47.622052, -122.345869));
//    map = new Microsoft.Maps.Map(document.getElementById('SDKmap'), {credentials: 'Your Bing Maps Key', bounds: boundingBox});
    //���������㲻֪������������


    function initMap(){
        var initObj = {
            credentials:key,//map key  http://www.bingmapsportal.com/ ��������
            enableClickableLogo:false,//����logo
            enableSearchLogo:false,      //������
            showDashboard:true,    //������
            showMapTypeSelector:true,  // ��������trueʱ �Ƿ���ʾautoMaticѡ��
            showScalebar:false,         //��������trueʱ���Ƿ���ʾ�Ŵ���С�ؼ�
//        backgroundColor : new Microsoft.Maps.Color(Math.round(255*Math.random()),Math.round(255*Math.random()),Math.round(255*Math.random()),Math.round(255*Math.random())),
//        disablePanning:true,        //��֪�������
//        bounds:boundingBox,         //viewOptions
            center: new Microsoft.Maps.Location(47.609771, -122.2321543125),//���ĵ����� ��
            zoom: 8, // ������
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