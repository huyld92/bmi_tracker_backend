/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import java.util.Optional;

/**
 *
 * @author Duc Huy
 * @param <T>
 */
public interface GeneralService<T> {

    Iterable<T> findAll();

    Optional<T> findById(Integer id);

    T save(T t);

}
