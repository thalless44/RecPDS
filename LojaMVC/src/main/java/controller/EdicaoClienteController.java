package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;

public class EdicaoClienteController {

    private Stage stage;
    private Cliente cliente;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEndereco;

    private Runnable onClienteAtualizado;

    @FXML
    private TextField txtTelefone;

    @FXML
    private DatePicker dataNascimento; // Usando DatePicker para a data de nascimento

    // Método que recebe o cliente selecionado e preenche os campos de edição
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        // Preenche os campos com os dados do cliente
        txtNome.setText(cliente.getNome());
        txtEndereco.setText(cliente.getEndereco());
        txtTelefone.setText(cliente.getTelefone());

        // Preenche o DatePicker com a data de nascimento do cliente
        if (cliente.getDataNascimento() != null) {
            dataNascimento.setValue(cliente.getDataNascimento().toLocalDate());
        }
    }

    // Método para salvar as alterações feitas no cliente
    @FXML
    void onClickSalvar(ActionEvent event) {
        if (cliente != null) {
            cliente.setNome(txtNome.getText());
            cliente.setEndereco(txtEndereco.getText());
            cliente.setTelefone(txtTelefone.getText());

            try {
                if (dataNascimento.getValue() != null) {
                    java.sql.Date data = java.sql.Date.valueOf(dataNascimento.getValue());
                    cliente.setDataNascimento(data);
                } else {
                    PrincipalController.mostrarAlerta("Erro", "A data de nascimento não pode ser vazia.");
                    return;
                }
            } catch (Exception e) {
                PrincipalController.mostrarAlerta("Erro", "Formato de data inválido.");
                return;
            }

            // Chama o método da DAO para atualizar o cliente no banco de dados
            ClienteDAO dao = new ClienteDAO();
            boolean sucesso = dao.atualizarCliente(cliente);

            if (sucesso) {
                if (onClienteAtualizado != null) {
                    onClienteAtualizado.run(); 
                    PrincipalController.mostrarAlerta("Sucesso", "Cliente atualizado com sucesso!");

                stage.close();
                }
                
            } else {
                PrincipalController.mostrarAlerta("Erro", "Erro ao atualizar cliente.");
            }
        }
    }

    // Método para fechar a janela de edição sem salvar
    @FXML
    void onClickSair(ActionEvent event) {
        stage.close();
    }

    // Método para definir o stage da tela de edição
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    

    public void setOnClienteAtualizado(Runnable callback) {
        this.onClienteAtualizado = callback;
    }
}
