<html>

<body>
<h1>Hi</h1>

<script type="text/javascript">
    var IdealTimeOut = 10; //10 seconds
    var idleSecondsTimer = null;
    var idleSecondsCounter = 0;
    document.onclick = function () { idleSecondsCounter = 0; };
    document.onmousemove = function () { idleSecondsCounter = 0; };
    document.onkeypress = function () { idleSecondsCounter = 0; };
    idleSecondsTimer = window.setInterval(CheckIdleTime, 1000);

    function CheckIdleTime() {
        idleSecondsCounter++;
        var oPanel = document.getElementById("timeOut");
        if (oPanel) {
            oPanel.innerHTML = (IdealTimeOut - idleSecondsCounter);
        }
        if (idleSecondsCounter >= IdealTimeOut) {
            window.clearInterval(idleSecondsTimer);
            alert("Your Session has expired. Please login again.");
            window.location = "https://www.aspsnippets.com/";
        }
    }
</script>
</body>
</html>