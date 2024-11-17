package com.easyride.payment_service.service;

import com.easyride.payment_service.dto.WalletDto;
import com.easyride.payment_service.model.Payment;
import com.easyride.payment_service.model.Wallet;
import com.easyride.payment_service.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void addFunds(Long orderId, Double amount) {
        // 获取订单对应的司机ID
        Long driverId = getDriverIdByOrderId(orderId);

        Wallet wallet = walletRepository.findById(driverId)
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet();
                    newWallet.setDriverId(driverId);
                    newWallet.setBalance(0.0);
                    newWallet.setUpdatedAt(LocalDateTime.now());
                    return newWallet;
                });

        // 计算平台服务费
        Double serviceFee = calculateServiceFee(amount);

        // 更新钱包余额
        wallet.setBalance(wallet.getBalance() + amount - serviceFee);
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);
    }

    @Override
    public WalletDto getWallet(Long driverId) {
        Wallet wallet = walletRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("钱包不存在"));

        return new WalletDto(wallet.getDriverId(), wallet.getBalance(), wallet.getUpdatedAt());
    }

    @Override
    public List<Payment> getEarnings(Long driverId, LocalDateTime from, LocalDateTime to) {
        // 查询指定时间范围内的收入明细
        // 需要从 PaymentRepository 中查询与该司机相关的支付记录
        return null;
    }

    private Long getDriverIdByOrderId(Long orderId) {
        // 通过与 order_service 通信，获取订单对应的司机ID
        return null; // 实现通信逻辑
    }

    private Double calculateServiceFee(Double amount) {
        // 计算平台服务费，例如按 10% 收取
        return amount * 0.10;
    }
}

