package com.produtos.produtos.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.produtos.produtos.Entities.ProdutosEntity;
import com.produtos.produtos.Repositories.ProdutoRepositorio;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProdutosController {

    private static String caminhoimg = "/img/";

    @Autowired
    private ProdutoRepositorio pRepositorio;

    public ProdutosController() {

    }

    @GetMapping("/")
    public Iterable<ProdutosEntity> getall() {
        return pRepositorio.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<String> salvar(@RequestBody ProdutosEntity produto) {
        pRepositorio.save(produto);
        return ResponseEntity.ok().body("Produto salvo com sucesso!");
        // try {
        // pRepositorio.save(produto);
        // return ResponseEntity.ok().body("Produto salvo com sucesso!");
        // } catch (Exception e) {
        // return ResponseEntity.status(500).body("ERRO:\n\t"+e.toString());
        // }
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> slvimg(MultipartFile arquivo, @PathVariable Integer id) {
        try {
            if (!arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoimg + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);
                ProdutosEntity prd = pRepositorio.findById(id).get();
                prd.setImg(caminhoimg + arquivo.getOriginalFilename());
                pRepositorio.save(prd);
                // ImgModel img = new ImgModel();
                // img.setEndereco(caminhoimg+arquivo.getOriginalFilename());
                // img.setNome(arquivo.getOriginalFilename());
                // iRepository.save(img);
                return ResponseEntity.ok("Imagem Cadastrada com Sucesso");
            } else {
                return ResponseEntity.status(406).body("Arquivo Vazio");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERRO:\n\t" + e.toString());
        }
    }
    @GetMapping("/{id}")
    public byte[] getimg(@PathVariable Integer id) throws IOException{
        ProdutosEntity prd = pRepositorio.findById(id).get();
        File imagemarq = new File(prd.getImg());
        return Files.readAllBytes(imagemarq.toPath());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id){
        try {
            pRepositorio.deleteById(id);
            return ResponseEntity.ok().body("Produto deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível deletar este produto\n\t"+e.toString());
        }
    }
    @PutMapping("/")
    public ResponseEntity<String> editar(@RequestBody ProdutosEntity produto) {
        pRepositorio.save(produto);
        return ResponseEntity.ok().body("Produto editado com sucesso!");
        // try {
        // pRepositorio.save(produto);
        // return ResponseEntity.ok().body("Produto salvo com sucesso!");
        // } catch (Exception e) {
        // return ResponseEntity.status(500).body("ERRO:\n\t"+e.toString());
        // }
    }
}
