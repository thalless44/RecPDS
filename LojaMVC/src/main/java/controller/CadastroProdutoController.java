package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;

public class CadastroProdutoController {

    private Stage stage;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtQEstoque;

    @FXML
    private TextField txtValor;

    // Método para cadastrar o produto
    @FXML
    void onClickCadastrarProdutos(ActionEvent event) throws IOException {
        String nome = txtNome.getText();
        String quantidadeEstoqueStr = txtQEstoque.getText();
        String valorStr = txtValor.getText();

        // Validação simples dos campos
        if (nome.isEmpty() || quantidadeEstoqueStr.isEmpty() || valorStr.isEmpty()) {
            PrincipalController.mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            int quantidadeEstoque = Integer.parseInt(quantidadeEstoqueStr);
            double valor = Double.parseDouble(valorStr);

            Produto produto = new Produto(nome, quantidadeEstoque, valor);
            ProdutoDAO dao = new ProdutoDAO();

            
            boolean sucesso = dao.cadastrarProduto(produto);
            if (sucesso) {
                PrincipalController.mostrarAlerta("Sucesso", "Produto cadastrado!");
                URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                Stage telaPrincipal = new Stage();
                PrincipalController pc = loader.getController();
                pc.setStage(telaPrincipal);

                Scene scene = new Scene(root);
                telaPrincipal.setScene(scene);
                telaPrincipal.show();
                stage.close();
            } else {
                PrincipalController.mostrarAlerta("Erro", "Erro ao cadastrar .");
            }

        } catch (NumberFormatException e) {
            PrincipalController.mostrarAlerta("Erro", "Quantidade de estoque e valor devem ser números válidos.");
        }
    }

    // Método para fechar a tela sem salvar
    @FXML
    void onClickSair(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaPrincipal = new Stage();
        PrincipalController pc = loader.getController();
        pc.setStage(telaPrincipal);
        
        Scene scene = new Scene (root);
        telaPrincipal.setScene(scene);
        telaPrincipal.show();
        stage.close();
    }

    // Definir o Stage da tela
    public void setStage(Stage stage) {
        this.stage = stage;
    }

   
}
