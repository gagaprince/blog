<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<aside1>
<div class="datepickerFrame">
    <ol>
        <span id="date" style="display:block;position:relative;"></span>
        <script type="text/javascript" src="/blog/js/datepicker/js/datepicker.js"></script>
        <link href="/blog/css/lrtk.css" rel="stylesheet">
        <link rel="stylesheet" href="/blog/js/datepicker/css/datepicker.css" type="text/css" />
        <script>
            $(document).ready(function(){
                function getNowDate(){
                    var date = new Date();
                    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
                }
                $('#date').DatePicker({
                    flat: true,
                    date: getNowDate(),
                    current: getNowDate(),
                    calendars: 1,
                    starts: 1
                });
            });
        </script>
    </ol>
</div>
</aside1>