package gift.service;

import gift.Entity.Product;
import gift.Entity.Option;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    // if문으로 제어해보기
    public boolean validateProduct(Product product, BindingResult bindingResult) {
        if (product.getName().contains("카카오") && !product.getMDapproved()) {
            bindingResult.rejectValue("name", "forbidden.word", "상품 이름에 '카카오'는 포함할 수 없습니다.");
            return false;
        }
        return true;
    }

    // try catch문으로 제어해보기
    public void validateProductException(Product product) {
        if (product.getName().contains("카카오") && !product.getMDapproved()) {
            throw new IllegalArgumentException("상품 이름에 '카카오'는 포함할 수 없습니다.");
        }
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // 옵션
    public List<Option> findOptionsByProductId(Long productId) {
        return optionRepository.findByProductId(productId);
    }

    public Option findOptionById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateProduct(Long id, Product product) {
        Product existing = productRepository.findById(id).orElseThrow();

        // 옵션 먼저 삭제
        optionRepository.deleteByProductId(id);

        // 기존 상품 정보 업데이트
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setImageUrl(product.getImageUrl());
        existing.setMDapproved(product.getMDapproved());

        // 새 옵션 세팅
        if (product.getOptions() != null) {
            for (Option opt : product.getOptions()) {
                opt.setProduct(existing); // 양방향 연관
            }
            existing.getOptions().clear(); // 리스트 클리어
            existing.getOptions().addAll(product.getOptions());
        }

        productRepository.save(existing);
    }


}
