var client = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    client = Stomp.client('ws://192.168.1.6:8080/api/cmdr/listenForEntries');
    client.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        client.subscribe('/api/stomp/entries', function (json) {
            showGreeting(JSON.stringify(JSON.parse(json.body), undefined, '\t'));
        });
    })
}

function disconnect() {
    if (client !== null) {
        client.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
    $("#messages").append('<pre><code class="json">' + message + '</code></pre>');

    $("#messages").each(function (i, block) {
        hljs.highlightBlock(block)
    })
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
});