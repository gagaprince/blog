(function(window){
    function Map(){}
    Map.prototype={
        mapConfig:null,
        map:null,
        initConfig:function(){
            //    var boundingBox = Microsoft.Maps.LocationRect.fromLocations(new Microsoft.Maps.Location(47.618594, -122.347618), new Microsoft.Maps.Location(47.620700, -122.347584), new Microsoft.Maps.Location(47.622052, -122.345869));
            //    map = new Microsoft.Maps.Map(document.getElementById('SDKmap'), {credentials: 'Your Bing Maps Key', bounds: boundingBox});
            //这里三个点不知道是哪三个点
            this.mapConfig={
                credentials:'An7mI-ncdAXO6T4Pzie8SwYcyKDFmwA-t-EHHEMpU9TmsyFKXXAGVWo4ITNfhdUI',//map key  http://www.bingmapsportal.com/ 可以申请
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
        },
        init:function(params){
            this.initConfig();
            this.mapConfig = $.extend(params,this.mapConfig);
            this.initMap();
            this.log();
        },
        initMap:function(){
            var myMap = document.getElementById('myMap');
            myMap.oncontextmenu = function(){return false;};
            this.map = new Microsoft.Maps.Map(myMap,this.mapConfig);
        },

        addMark:function(p){
            var map = this.map;
            var pushpinOptions = {draggable: true};
            var pushpin= Map.Pushpin(p||map.getCenter(), pushpinOptions);
            map.entities.push(pushpin);
            return pushpin;
        },
        addTipMark:function(font,p){
            var map = this.map;
            font = font||"自定义";
            var html="<div style='font-size:12px;font-weight:bold;border:solid 2px;background-color:LightBlue;width:"+font.length*14+"px;"+"'>"+font+"</div>";
            var pushpinOptions = {width:null,height: null,htmlContent:html,draggable: true};
            var pushpin= Map.Pushpin(p||map.getCenter(), pushpinOptions);
            map.entities.push(pushpin);
            return pushpin;
        },

        linePoints:function(locations,options,polyline){
            options = $.extend({
                strokeColor:Map.Color(Math.round(255),Math.round(0),Math.round(0),Math.round(255)),
                strokeThickness: 3
            },options);
            if(!polyline){
                var map = this.map;
                polyline = Map.Polyline([]);
                map.entities.push(polyline);
            }
            polyline.setLocations(locations);
            return polyline;
        },

        fillPoints:function(locations,options,polygon){
            options = $.extend({
                strokeColor: Map.Color(Math.round(255),Math.round(0),Math.round(0),Math.round(255)),
                strokeThickness: 3,
                fillColor:Map.Color(Math.round(0),Math.round(255),Math.round(0),Math.round(122))
            },options);
            if(!polygon){
                var map = this.map;
                polygon = Map.Polygon([]);
                map.entities.push(polygon);
            }
            polygon.setLocations(locations);
            return polygon;
        },
        removePolyline:function(polyline){
            var map = this.map;
            if(polyline instanceof Microsoft.Maps.Polyline){
                map.entities.remove(polyline);
            }
        },

        addMarkClickListener:function(mark,callback){
            return this._addMarkListener("click",mark,callback);
        },
        addMarkLeftClickListener:function(mark,callback){
            return this._addMarkListener("leftclick",mark,callback);
        },
        addMarkRightClickListener:function(mark,callback){
            return this._addMarkListener("rightclick",mark,callback);
        },
        addMarkDragStartListener:function(mark,callback){
            return this._addMarkListener("dragstart",mark,callback);
        },
        addMarkDragListener:function(mark,callback){
            return this._addMarkListener("drag",mark,callback);
        },
        addMarkDragEndListener:function(mark,callback){
            return this._addMarkListener("dragend",mark,callback);
        },
        removeMarkEvent:function(event){
            if(event)Microsoft.Maps.Events.removeHandler(event);
        },

        addMapClickListener:function(callback){
            return this._addMapListener("click",callback);
        },
        addMapRightClickListener:function(callback){
            return this._addMapListener("rightclick",callback);
        },
        addMapDClickListener:function(callback){
            return this._addMapListener("dblclick",callback);
        },

        _addMapListener:function(eventName,callback){
            var map = this.map;
            var _this = this;
            var event= Microsoft.Maps.Events.addHandler(map,eventName, function(e){
                var eventName = e.eventName;
                if(eventName=="click"||eventName=="leftclick"||eventName=="rightclick"){
                    var clickLocation = Map.p(e.pageY, e.pageX);
                    var location = _this._transformPage2Location(clickLocation);
                }else{

                }
                if(callback){
                    callback(e,location);
                }
            });
            return event;
        },

        _transformPage2Location:function(page){
            var map = this.map;
            var width = map.getWidth();
            var height = map.getHeight();
            var bounds = map.getBounds();
            var bheight = bounds.height;
            var bwidth = bounds.width;
            var centerLocation = bounds.center;
            var viewportX = map.getViewportX();
            var viewportY = map.getViewportY();
            var rateX = bwidth/width;
            var rateY = bheight/height;
            var locationx = centerLocation.longitude+(page.longitude-viewportX)*rateX;
            var locationy = centerLocation.latitude-(page.latitude-viewportY)*rateY;
            if(Math.abs(locationx)>180){
                locationx = (Math.abs(locationx)-360)*locationx/Math.abs(locationx);
            }
            return Map.p(locationy,locationx);
        },

        _addMarkListener:function(eventName,mark,callback){
            if(!mark){
                throw "mark is null";
            }
            var event= Microsoft.Maps.Events.addHandler(mark,eventName, function(e){
                var eventName = e.eventName;
                if(eventName=="click"||eventName=="leftclick"||eventName=="rightclick"){
                    var location = e.target._location;
                }else{
                    var location = e.entity._location;
                }
                if(callback){
                    callback(e,location);
                }
            });
            return event;
        },

        getRealMap:function(){
            return this.map;
        },

        log:function(){
            var map = this.map;
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
    }
    Map.createMap = function(params){
        var myMap = new Map();
        myMap.init(params);
        return myMap;
    }
    Map.p =Map.Location = function(y,x){
        //latitude 维度 longitude经度
        return new Microsoft.Maps.Location(y,x);
    }
    Map.c = Map.Color = function(r,g,b,a){
        return new Microsoft.Maps.Color(r,g,b,a);
    }
    Map.pg = Map.Polygon=function(locations){
        return new Microsoft.Maps.Polygon(locations);
    }
    Map.pl = Map.Polyline=function(locations){
        return new Microsoft.Maps.Polyline(locations);
    }
    Map.pp = Map.Pushpin = function(location,params){
        return new Microsoft.Maps.Pushpin(location,params);
    }


    window.Map = Map;
})(window);