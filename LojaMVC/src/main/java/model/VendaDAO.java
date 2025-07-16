package model;

import dal.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    // INSERIR
    public boolean cadastrarVenda(Venda venda) {
        String sqlVenda = "INSERT INTO venda (id_produto, id_cliente, quantidade, preco, dataVenda) VALUES (?, ?, ?, ?, ?)";
        String sqlAtualizarEstoque = "UPDATE produto SET quantidade = quantidade - ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtEstoque = conn.prepareStatement(sqlAtualizarEstoque)) {

            // Inserir a venda
            stmtVenda.setInt(1, venda.getProdutoId());
            stmtVenda.setInt(2, venda.getClienteId());
            stmtVenda.setInt(3, venda.getQuantidade());
            stmtVenda.setDouble(4, venda.getPreco());
            stmtVenda.setDate(5, venda.getDataVenda());

            int linhasAfetadas = stmtVenda.executeUpdate();

            if (linhasAfetadas > 0) {
                // Obter o ID da venda inserida (caso precise para outras operações)
                ResultSet generatedKeys = stmtVenda.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idVenda = generatedKeys.getInt(1);
                    venda.setId(idVenda);  // Atualizar o ID da venda no objeto
                }

                // Atualizar o estoque do produto
                stmtEstoque.setInt(1, venda.getQuantidade());
                stmtEstoque.setInt(2, venda.getProdutoId());

                int estoqueAtualizado = stmtEstoque.executeUpdate();
                return estoqueAtualizado > 0;  // Se o estoque for atualizado, a venda foi registrada com sucesso

            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ATUALIZAR
    public boolean atualizarVenda(Venda venda) {
        String sql = "UPDATE venda SET id_cliente = ?, id_produto = ?, quantidade = ?, preco = ?, dataVenda = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getClienteId());
            stmt.setInt(2, venda.getProdutoId());
            stmt.setInt(3, venda.getQuantidade());
            stmt.setDouble(4, venda.getPreco());
            stmt.setDate(5, venda.getDataVenda());
            stmt.setInt(6, venda.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // EXCLUIR
    public boolean excluirVenda(int idVenda) {
        String sql = "DELETE FROM venda WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // BUSCAR POR ID
    public Venda buscarVendaPorId(int id) {
        String sql = "SELECT * FROM venda WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Venda venda = new Venda(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco"),
                        rs.getDate("dataVenda")
                );
                return venda;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // LISTAR TODAS AS VENDAS
    public List<Venda> listarTodasVendas() {
        String sql = "SELECT * FROM venda";
        List<Venda> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco"),
                        rs.getDate("dataVenda")
                );
                lista.add(venda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
