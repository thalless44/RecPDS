package controller;

import static controller.PrincipalController.mostrarAlerta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import model.Cliente;
import model.Produto;
import model.Venda;
import model.VendaDAO;
import model.ProdutoDAO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javafx.stage.Stage;
import model.ClienteDAO;

public class EfetuarVendaController {

    private Stage stage;

    @FXML
    private ComboBox<Cliente> cbxCliente;

    @FXML
    private DatePicker dataVenda;

    @FXML
    private Label lblProduto;

    @FXML
    private Label lblQproduto1;

    @FXML
    private Label lblValorProduto;

    private Produto produtoSelecionado; 
    private Runnable onVendaAtualizado;

    
    public void initialize() {
        carregarClientes();
    }

    
    public void carregarClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();  // Instanciando o ClienteDAO
        List<Cliente> listaClientes = clienteDAO.listarTodosClientes();
        cbxCliente.getItems().addAll(listaClientes);  // Adiciona os novos clientes
    }

    
    public void setProduto(Produto produto) {
        this.produtoSelecionado = produto;
        lblProduto.setText(produto.getNome());
        lblQproduto1.setText(String.valueOf(produto.getQuantidadeEstoque()));
        lblValorProduto.setText(String.format("R$ %.2f", produto.getValor()));
    }

    // Método para efetuar a venda
    @FXML
    void onClickVenda(ActionEvent event) {
        // Verifica se o cliente foi selecionado
        Cliente clienteSelecionado = cbxCliente.getSelectionModel().getSelectedItem();
        if (clienteSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um cliente.");
            return;
        }

        // Verifica se a data da venda foi selecionada
        if (dataVenda.getValue() == null) {
            mostrarAlerta("Erro", "Selecione uma data para a venda.");
            return;
        }

        // Verifica se há estoque disponível
        int quantidadeVenda = Integer.parseInt(lblQproduto1.getText());
        if (quantidadeVenda <= 0) {
            mostrarAlerta("Erro", "Produto sem estoque disponível.");
            return;
        }

        // Converte LocalDate para java.sql.Date
        LocalDate localDate = dataVenda.getValue();
        Date sqlDate = Date.valueOf(localDate);

        double precoVenda = produtoSelecionado.getValor();

        Venda venda = new Venda(clienteSelecionado.getId(), produtoSelecionado.getId(), 1, precoVenda, sqlDate);

        // Cadastra a venda no banco de dados
        VendaDAO vendaDAO = new VendaDAO();
        boolean vendaRegistrada = vendaDAO.cadastrarVenda(venda);

            if (onVendaAtualizado != null) {
                    onVendaAtualizado.run(); 
                mostrarAlerta("Sucesso", "Venda realizada com sucesso e estoque atualizado!");
                stage.close();
               
                
            }  else {
                mostrarAlerta("Erro", "Erro ao atualizar o estoque do produto.");
            }
                
        }
    

   

    @FXML
    void onClickSair(ActionEvent event) {
        Stage stage = (Stage) cbxCliente.getScene().getWindow();
        stage.close();
    }

    void setStage(Stage telaEfetuarVenda) {
        this.stage = telaEfetuarVenda;
    }
    public void setOnVendaAtualizado(Runnable callback) {
    this.onVendaAtualizado = callback;
}
}
