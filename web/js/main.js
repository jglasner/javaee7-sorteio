ws = new WebSocket("ws://localhost:8080/javaee7-sorteio/sorteioEndpoint");

ws.onmessage = function(evt) {
    $("#divResultado").html("<h1>" + evt.data + "</h1>");
};

$("#btnSortear").click(function() {
    ws.send("sortear");
});

$("#incluir").click(function() {
    var nome = $("#nome").val();
    $("#nome").val("");
    addNome(nome);
    ws.send("nome " + nome);
});

function addNome(nome) {
    $("#participantes").append('<li>' + nome + '</li>');
    
}