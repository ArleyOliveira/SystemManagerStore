/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.systemmanagerstore.Presentation;

import br.com.systemmanagerstore.DataAccess.EnderecoDAO;
import br.com.systemmanagerstore.DataAccess.PessoaDAO;
import br.com.systemmanagerstore.DomainModel.Endereco;
import br.com.systemmanagerstore.DomainModel.Funcionario;
import br.com.systemmanagerstore.DomainModel.Pessoa;
import br.com.systemmanagerstore.DomainModel.Telefone;
import java.util.Date;

/**
 *
 * @author ARLEY
 */
public class Teste {
    public static void main(String[] args) {
        PessoaDAO pDAO = new PessoaDAO();
        
        Pessoa pessoa = new Pessoa();
        
        pessoa.setCpf("131.000.000-00");
        pessoa.setNome("Arley Oliveira");
        pessoa.setDataNascimento(new Date());
        pessoa.setSexo('M');
        pessoa.setTelefone(new Telefone("(39) 9184-8038", "Celular"));
        
        pDAO.Salvar(pessoa);
        
        for (Pessoa p : pDAO.Buscar(pessoa)) {
            System.out.println(p.getNome() + " | " + p.getCpf());
        }
        
        pessoa = new Funcionario();
        
        pessoa = (Funcionario) pessoa;
        pessoa.setCpf("131.000.000-10");
        pessoa.setNome("Arilson Oliveira");
        pessoa.setDataNascimento(new Date());
        pessoa.setSexo('M');
        pessoa.setTelefone(new Telefone("(39) 9184-8038", "Celular"));
        
        ((Funcionario) pessoa).setEmail("arley.msn@hotmail.com");
        ((Funcionario) pessoa).setSenhaCriptografada("123");
        
        pDAO.Salvar(pessoa);
        
        for (Pessoa p : pDAO.Buscar(new Pessoa())) {
            System.out.println(p.getNome() + " | " + p.getCpf());
        }
    }
}
