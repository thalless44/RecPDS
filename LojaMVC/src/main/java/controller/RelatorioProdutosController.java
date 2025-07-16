package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;
import model.Venda;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javafx.application.Platform;

public class RelatorioProdutosController {

    private Stage stage;

    @FXML
    private TableView<Produto> tbProdutos;

    @FXML
    private TableColumn<Produto, Integer> cID;

    @FXML
    private TableColumn<Produto, String> cNome;

    @FXML
    private TableColumn<Produto, Integer> cQuantidade;

    @FXML
    private TableColumn<Produto, Double> cValor;

  
    @FXML
    void initialize() {
        
        carregarProdutos();
    }

    // Método para carregar os produtos na TableView
    public void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> listaProdutos = dao.listarTodosProdutos();  // Busca todos os produtos do banco
        ObservableList<Produto> produtosObservable = FXCollections.observableArrayList(listaProdutos);

        // Define as células da tabela
        cID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        cNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        cQuantidade.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantidadeEstoque()).asObject());
        cValor.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValor()).asObject());

        
        tbProdutos.setItems(produtosObservable);
    }

    
    @FXML
    void onClickVenda(ActionEvent event) throws IOException {
        
        Produto produtoSelecionado = tbProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            
            URL url = new File("src/main/java/view/EfetuarVenda.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage telaEfetuarVenda = new Stage();
            EfetuarVendaController evController = loader.getController();

            Platform.runLater(() -> {
                evController.setProduto(produtoSelecionado);
            });
            evController.setOnVendaAtualizado(() -> carregarProdutos());
            evController.setStage(telaEfetuarVenda);

            Scene scene = new Scene(root);
            telaEfetuarVenda.setScene(scene);
            telaEfetuarVenda.show();
        } else {
            
            PrincipalController.mostrarAlerta("Erro", "Selecione um produto para efetuar a venda.");
        }
    }

    
    @FXML
    void onClickEditar(ActionEvent event) throws IOException {
        Produto produtoSelecionado = tbProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            
            URL url = new File("src/main/java/view/EdicaoProduto.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage telaEdicaoProduto = new Stage();
            EdicaoProdutoController ep = loader.getController();

            ep.setOnProdutoAtualizado(() -> carregarProdutos());
            // Passa o produto selecionado para a tela de edição
            ep.setProduto(produtoSelecionado);
            ep.setStage(telaEdicaoProduto);

            Scene scene = new Scene(root);
            telaEdicaoProduto.setScene(scene);
            telaEdicaoProduto.show();
        } else {
            PrincipalController.mostrarAlerta("Erro", "Selecione um produto para editar.");
        }
    }

   
    @FXML
    void onClickExcluir(ActionEvent event) {
        Produto produtoSelecionado = tbProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            ProdutoDAO dao = new ProdutoDAO();
            boolean sucesso = dao.excluirProduto(produtoSelecionado.getId());

            if (sucesso) {
                tbProdutos.getItems().remove(produtoSelecionado);
                PrincipalController.mostrarAlerta("Sucesso", "Produto excluído com sucesso!");
            } else {
                PrincipalController.mostrarAlerta("Erro", "Algun produto desse estoque já foi vendido");
            }
        } else {
            PrincipalController.mostrarAlerta("Erro", "Selecione um produto para excluir.");
        }
    }

    

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

    // Método para setar o Stage da tela
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
