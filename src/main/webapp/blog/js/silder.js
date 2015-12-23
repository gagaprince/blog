var obj=null;
var As=document.getElementById('nav').getElementsByTagName('a');
for(var i=0;i<As.length;i++){
    if(window.location.href.indexOf(As[i].href)>=0)
    obj=As[i];
}
if(obj!=null)
    obj.id='nav_current';
