package model;

import dal.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // INSERIR
    public boolean cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, quantidade, valor) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidadeEstoque());
            stmt.setDouble(3, produto.getValor());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ATUALIZAR
    public boolean atualizarProduto(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidadeEstoque());
            stmt.setDouble(3, produto.getValor());
            stmt.setInt(4, produto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // EXCLUIR
    public boolean excluirProduto(int idProduto) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // BUSCAR POR ID
    public Produto buscarProdutoPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto produto = new Produto(
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDouble("valor")
                );
                produto.setId(rs.getInt("id"));
                return produto;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // LISTAR TODOS OS PRODUTOS
    public List<Produto> listarTodosProdutos() {
        String sql = "SELECT * FROM produto";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDouble("valor")
                );
                produto.setId(rs.getInt("id"));
                lista.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean atualizarQuantidadeEstoque(int idProduto, int quantidadeVendida) {
        String sql = "UPDATE produto SET quantidade = quantidade - ? WHERE id = ?";

        try (Connection conn = ConexaoBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeVendida);
            stmt.setInt(2, idProduto);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
