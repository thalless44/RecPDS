package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;

public class EdicaoProdutoController {

    private Stage stage;
    private Produto produto;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtValor;
    
    private Runnable onProdutoAtualizado;

    // Método para carregar os dados do produto na tela de edição
    public void setProduto(Produto produto) {
        this.produto = produto;

        // Preenche os campos com os dados do produto
        txtNome.setText(produto.getNome());
        txtQuantidade.setText(String.valueOf(produto.getQuantidadeEstoque()));
        txtValor.setText(String.valueOf(produto.getValor()));
    }

    // Método para salvar as alterações feitas no produto
    @FXML
    void onClickEditarProdutos(ActionEvent event) {
        if (produto != null) {
            produto.setNome(txtNome.getText());

            try {
                produto.setQuantidadeEstoque(Integer.parseInt(txtQuantidade.getText()));
            } catch (NumberFormatException e) {
                PrincipalController.mostrarAlerta("Erro", "Quantidade inválida.");
                return;
            }

            try {
                produto.setValor(Double.parseDouble(txtValor.getText()));
            } catch (NumberFormatException e) {
                PrincipalController.mostrarAlerta("Erro", "Valor inválido.");
                return;
            }

            // Atualiza o produto no banco de dados
            ProdutoDAO dao = new ProdutoDAO();
            boolean sucesso = dao.atualizarProduto(produto);

            if (sucesso) {
                if (onProdutoAtualizado != null) {
                    onProdutoAtualizado.run();
                // Fecha a tela de edição após salvar
                stage.close();
                PrincipalController.mostrarAlerta("Sucesso", "Produto atualizado com sucesso!");
            } else {
                    PrincipalController.mostrarAlerta("Erro", "produto não atualizado");
            }
        }}}
    


    @FXML
    void onClickSair(ActionEvent event) {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    
    
    public void setOnProdutoAtualizado(Runnable callback) {
    this.onProdutoAtualizado = callback;
}
    
}
