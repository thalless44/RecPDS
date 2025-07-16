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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Venda;
import model.VendaDAO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import model.ClienteDAO;
import model.ProdutoDAO;

public class PrincipalController {

    private Stage stagePrincipal;

    @FXML
    private TableView<Venda> tableViewVenda;

    @FXML
    private TableColumn<Venda, String> cCliente;

    @FXML
    private TableColumn<Venda, String> cProduto;

    @FXML
    private TableColumn<Venda, Integer> cId;

    @FXML
    private TableColumn<Venda, Double> cPreco;

    @FXML
    private TableColumn<Venda, Integer> cQuantidade;

    @FXML
    private MenuItem cadastrarCliente;

    @FXML
    private MenuItem cadastrarProduto;

    @FXML
    private Label lblUsuario;

    @FXML
    private Menu menuAjuda;

    @FXML
    private Menu menuCadastro;

    @FXML
    private MenuItem menuCadastroUsuarios;

    @FXML
    private MenuItem menuFechar;

    @FXML
    private MenuItem menuRelatorioUsuarios;

    @FXML
    private MenuItem menuRelatorioClientes;

    @FXML
    private MenuItem menuRelatorioProdutos;

    @FXML
    private Menu menuRelatorios;

    @FXML
    private MenuItem menuSobre;

    // Método de inicialização
    @FXML
    void initialize() {
        carregarVendas();
    }

    // Método para carregar as vendas na TableView
    public void carregarVendas() {
        VendaDAO vendaDAO = new VendaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        List<Venda> listaVendas = vendaDAO.listarTodasVendas();
        ObservableList<Venda> vendasObservable = FXCollections.observableArrayList(listaVendas);

        cId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        cCliente.setCellValueFactory(cellData -> {
            int clienteId = cellData.getValue().getClienteId();
            String nomeCliente = "";
            if (clienteDAO.buscarClientePorId(clienteId) != null) {
                nomeCliente = clienteDAO.buscarClientePorId(clienteId).getNome();
            }
            return new SimpleStringProperty(nomeCliente);
        });

        cProduto.setCellValueFactory(cellData -> {
            int produtoId = cellData.getValue().getProdutoId();
            String nomeProduto = "";
            if (produtoDAO.buscarProdutoPorId(produtoId) != null) {
                nomeProduto = produtoDAO.buscarProdutoPorId(produtoId).getNome();
            }
            return new SimpleStringProperty(nomeProduto);
        });

        cQuantidade.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantidade()).asObject());
        cPreco.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPreco()).asObject());

        tableViewVenda.setItems(vendasObservable);
    }

    // Método para excluir a venda
    @FXML
    void onClickExcluirVenda(ActionEvent event) {
        Venda vendaSelecionada = tableViewVenda.getSelectionModel().getSelectedItem();

        if (vendaSelecionada != null) {
            VendaDAO dao = new VendaDAO();
            boolean sucesso = dao.excluirVenda(vendaSelecionada.getId());

            if (sucesso) {
                tableViewVenda.getItems().remove(vendaSelecionada);
                mostrarAlerta("Sucesso", "Venda excluída com sucesso!");
            } else {
                mostrarAlerta("Erro", "Erro ao excluir venda.");
            }
        } else {
      
            mostrarAlerta("Erro", "Selecione uma venda para excluir.");
        }
    }

    

 
    @FXML
    void onClickCadastrarCliente(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/CadastroCliente.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaCadastroCliente = new Stage();
        CadastroClienteController cd = loader.getController();
        
        cd.setStage(telaCadastroCliente);
        
        Scene scene = new Scene(root);
        telaCadastroCliente.setScene(scene);
        telaCadastroCliente.show();
        stagePrincipal.close();
    }

    @FXML
    void onClickCadastrarProduto(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/CadastroProduto.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaCadastroProduto = new Stage();
        CadastroProdutoController cp = loader.getController();
        cp.setStage(telaCadastroProduto);

        Scene scene = new Scene(root);
        telaCadastroProduto.setScene(scene);
        telaCadastroProduto.show();
        stagePrincipal.close();
    }

    @FXML
    void onClickRelatoriosCliente(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/RelatorioCliente.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaRelatorioCliente = new Stage();
        RelatorioClientesController rc = loader.getController();
        rc.setStage(telaRelatorioCliente);

        Scene scene = new Scene(root);
        telaRelatorioCliente.setScene(scene);
        telaRelatorioCliente.show();
        stagePrincipal.close();
    }

    @FXML
    void onClickRelatoriosProduto(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/RelatorioProduto.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaRelatorioProduto = new Stage();
        RelatorioProdutosController rp = loader.getController();
        rp.setStage(telaRelatorioProduto);

        Scene scene = new Scene(root);
        telaRelatorioProduto.setScene(scene);
        telaRelatorioProduto.show();
        stagePrincipal.close();
    }

    @FXML
    void onClickSair(ActionEvent event) {
        stagePrincipal.close();
    }

    public void setStage(Stage telaPrincipal) {
        this.stagePrincipal = telaPrincipal;
    }
    
    public static void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
