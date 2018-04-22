package me.silentdoer.ssmdemo.service;

import me.silentdoer.ssmdemo.po.Student;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 2:43 PM
 */
public interface StudentService {
    // ERP Logic layer, like login process etc.
    Student getOneUserWithLogic(Long uid);
}
