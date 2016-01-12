(function(window){
    var map = null;
    var key = 'An7mI-ncdAXO6T4Pzie8SwYcyKDFmwA-t-EHHEMpU9TmsyFKXXAGVWo4ITNfhdUI';
    function initMap(){
        map = new Microsoft.Maps.Map(document.getElementById('myMap'), key);
    }
    $(document).ready(function(){
        initMap();
    });
})(window);