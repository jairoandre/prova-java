<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.1.3/material.indigo-pink.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/default.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/dialog-polyfill.css">
    <title>Netprecision</title>
</head>

<body>

<div id="p2" class="mdl-progress mdl-js-progress mdl-progress__indeterminate" style="visibility: hidden; width: 100%;"></div>

<dialog id="addProdutoDlg" class="mdl-dialog">
    <h4 id="addProdutoDlgTitle" class="mdl-dialog__title">Pedido</h4>
    <div class="mdl-dialog__content">
        <span id="feedBack" style="color: darkred"></span>
        <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" id="codigoProduto">
            <label class="mdl-textfield__label" for="codigoProduto">Código produto...</label>
        </div>
        <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="quantidade">
            <label class="mdl-textfield__label" for="quantidade">Quantidade...</label>
            <span class="mdl-textfield__error">Somente número!</span>
        </div>
    </div>
    <div class="mdl-dialog__actions">
        <button type="button" class="mdl-button" onclick="addProduto()">Incluir</button>
        <button type="button" class="mdl-button close" onclick="hideProdutoDlg()">Cancelar</button>
    </div>
</dialog>

<dialog id="calcPrecoDlg" class="mdl-dialog">
    <h4 id="calcPrecoDlgTitle" class="mdl-dialog__title">Total</h4>
    <div class="mdl-dialog__content">
        <span id="feedBackPagamento" style="color: darkred"></span>
        <h4 id="totalPreco" style="width: 100%; text-align: right; color: forestgreen;"></h4>
        <div class="mdl-textfield mdl-js-textfield">
            <input class="mdl-textfield__input" type="text" pattern="-?[0-9]*(\.[0-9]+)?" id="pagamento" style="text-align: right;">
            <label class="mdl-textfield__label" for="pagamento">Pagamento...</label>
            <span class="mdl-textfield__error">Somente número!</span>
        </div>
        <h4 id="troco" style="width: 100%; text-align: right; color: rebeccapurple;"></h4>
    </div>
    <div class="mdl-dialog__actions">
        <button type="button" class="mdl-button" onclick="calcTroco()">Calcular</button>
        <button type="button" class="mdl-button close" onclick="calcPrecoDlg.close();">Fechar</button>
    </div>
</dialog>

<div class="demo-card-wide mdl-card mdl-shadow--2dp" style="margin: 10px auto;">
    <div class="mdl-card__title">
        <h2 class="mdl-card__title-text">Lanchonete</h2>
    </div>
    <div class="mdl-card__supporting-text" style="width: auto;">
        <table class="mdl-data-table mdl-js-data-table" width="100%">
            <thead>
                <tr>
                    <th width="75px">Id</th>
                    <th width="75px">Qtd.</th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="pedidos">

            </tbody>
        </table>
    </div>
    <div class="mdl-card__actions mdl-card--border">
        <button id="criarPedido" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">
            Criar Pedido
        </button>
    </div>
</div>

<script src="<%=request.getContextPath()%>/js/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.maskMoney.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dialog-polyfill.js"></script>
<script defer src="https://code.getmdl.io/1.1.3/material.min.js"></script>
<script>
    var ctxPath = "<%=request.getContextPath() %>";
    var _idPedido = null;
    var addProdutoDlgTitle$ = $('#addProdutoDlgTitle');
    var calcPrecoDlgTitle$ = $('#calcPrecoDlgTitle');
    var codigoProduto$ = $('#codigoProduto');
    var quantidade$ = $('#quantidade');
    var feedBack$ = $('#feedBack');
    var feedBackPagamento$ = $('#feedBackPagamento');
    var totalPreco$ = $('#totalPreco');
    var troco$ = $('#troco');

    Number.prototype.formatMoney = function(c, d, t){
        var n = this,
                c = isNaN(c = Math.abs(c)) ? 2 : c,
                d = d == undefined ? "." : d,
                t = t == undefined ? "," : t,
                s = n < 0 ? "-" : "",
                i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "",
                j = (j = i.length) > 3 ? j % 3 : 0;
        return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
    };


    var addProdutoDlg = document.querySelector('#addProdutoDlg');
    dialogPolyfill.registerDialog(addProdutoDlg);

    var calcPrecoDlg = document.querySelector('#calcPrecoDlg');
    dialogPolyfill.registerDialog(calcPrecoDlg);

    function calcTroco() {

        var valor = $('#pagamento').val();

        valor = valor.replace('.', '');
        valor = valor.replace(',', '.');

        if (valor === '') {
            valor = '0.00';
        }

        $.ajax({
            url: ctxPath + '/api/pedido/fechePedido',
            type: 'POST',
            data: JSON.stringify({id: _idPedido, valor: valor}),
            contentType: 'application/json',
            dataType: 'json',
            success: function(res) {
                feedBackPagamento$.empty();
                troco$.empty();
                if (+res === -2) {
                    feedBackPagamento$.append('Valor insuficiente.');
                } else {
                    troco$.append('R$ ' + res.formatMoney(2, ',', '.'));
                }
            }
        });

    }

    function calcPreco(id) {

        _idPedido = id;

        $.ajax({
            url: ctxPath + '/api/pedido/calculePrecoTotalPedido',
            type: 'POST',
            data: JSON.stringify({id: _idPedido}),
            contentType: 'application/json',
            dataType: 'json',
            success: function(res) {
                feedBackPagamento$.empty();
                troco$.empty();
                totalPreco$.empty();
                $('#pagamento').val('');
                if (+res === -1) {
                    feedBackPagamento$.append('Pedido não existe.');
                } else {
                    showCalcPrecoDlg(id, res);
                }
            }
        });

    }

    function clearVariables() {
        codigoProduto$.val('');
        quantidade$.val('');
        feedBack$.empty();
        feedBackPagamento$.empty();
    }

    function hideProdutoDlg() {
        addProdutoDlg.close();
    }

    function showCalcPrecoDlg(id, res) {
        _idPedido = id;
        totalPreco$.empty();
        totalPreco$.append('R$ ' + res.formatMoney(2, ',', '.'));
        calcPrecoDlgTitle$.empty();
        calcPrecoDlgTitle$.append('Pedido nº. ' + id);
        calcPrecoDlg.showModal();
    }

    function showAddProdutoDlg(id) {
        _idPedido = id;
        addProdutoDlgTitle$.empty();
        addProdutoDlgTitle$.append('Pedido nº. ' + id);
        addProdutoDlg.showModal();
    }

    function addProduto() {
        var codigo = codigoProduto$.val();
        var quantidade = quantidade$.val();
        $.ajax({
            url: ctxPath + '/api/pedido/adicioneProdutoNoPedido',
            type: 'POST',
            data: JSON.stringify({id: _idPedido, codigo: codigo, quantidade: quantidade}),
            contentType: 'application/json',
            dataType: 'json',
            success: function(res) {
                if (+res == -1) {
                    feedBack$.append('Pedido inexistente.');
                } else if (+res == -2) {
                    feedBack$.append('Produto inexistente.');
                } else if (+res == -9) {
                    feedBack$.append('Erro inesperado.');
                } else {
                    hideProdutoDlg();
                    clearVariables();
                    carregarPedidos();
                }
            }
        })

    }

    function carregarPedidos(){
        $.ajax({
            url: ctxPath + "/api/pedido/list",
            type: "GET",
            success: function(res) {
                var pedidos = $('#pedidos');
                pedidos.empty();
                res.map(function(p) {
                    var tr$ = $('<tr></tr>');
                    var tdId$ = $('<td></td>');
                    tdId$.append(p.id);
                    var tdQtd$ = $('<td></td>');
                    tdQtd$.append(p.itens);
                    var tdAcao$ = $('<td></td>');
                    var addProductButton$ = $('<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--mini-fab"><i class="material-icons">add</i></button>');
                    addProductButton$.id = 'item_' + p.id;
                    addProductButton$.on('click', function() {
                        showAddProdutoDlg(p.id);
                    });
                    var addLocalATM$ = $('<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--mini-fab" style="margin-left: 5px;"><i class="material-icons">local_atm</i></button>');
                    addLocalATM$.id = 'localAtm_' + p.id;
                    addLocalATM$.on('click', function() {
                        calcPreco(p.id);
                    });
                    tdAcao$.append(addProductButton$);
                    tdAcao$.append(addLocalATM$);
                    tr$.append(tdId$);
                    tr$.append(tdQtd$);
                    tr$.append(tdAcao$);
                    pedidos.append(tr$);
                });
            }
        });
    }

    $(function(){

        $(document).ajaxStart(function() {
            $("#p2" ).css('visibility', 'visible');
        });

        $(document).ajaxStop(function() {
            $("#p2" ).css('visibility', 'hidden');
        });

        $("#pagamento").maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});



        carregarPedidos();

        $("#criarPedido").on("click", function(){
            $.ajax({
                url: ctxPath + "/api/pedido/criarPedido",
                type: "GET",
                success: function(res) {
                    console.log(res);
                    carregarPedidos();
                }
            });
        });

    });
</script>
</body>

</html>