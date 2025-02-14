/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */

package org.elasticsearch.entitlement.qa.test;

import org.elasticsearch.core.SuppressForbidden;
import org.elasticsearch.entitlement.qa.entitled.EntitledActions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;
import java.util.Scanner;

import static org.elasticsearch.entitlement.qa.test.EntitlementTest.ExpectedAccess.PLUGINS;

@SuppressForbidden(reason = "Explicitly checking APIs that are forbidden")
class FileCheckActions {

    private static Path testRootDir = Paths.get(System.getProperty("es.entitlements.testdir"));

    private static Path readDir() {
        return testRootDir.resolve("read_dir");
    }

    private static Path readWriteDir() {
        return testRootDir.resolve("read_write_dir");
    }

    private static Path readFile() {
        return testRootDir.resolve("read_file");
    }

    private static Path readWriteFile() {
        return testRootDir.resolve("read_write_file");
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createScannerFile() throws FileNotFoundException {
        new Scanner(readFile().toFile());
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createScannerFileWithCharset() throws IOException {
        new Scanner(readFile().toFile(), StandardCharsets.UTF_8);
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createScannerFileWithCharsetName() throws FileNotFoundException {
        new Scanner(readFile().toFile(), "UTF-8");
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createFileOutputStreamString() throws IOException {
        new FileOutputStream(readWriteFile().toString()).close();
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createFileOutputStreamStringWithAppend() throws IOException {
        new FileOutputStream(readWriteFile().toString(), false).close();
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createFileOutputStreamFile() throws IOException {
        new FileOutputStream(readWriteFile().toFile()).close();
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void createFileOutputStreamFileWithAppend() throws IOException {
        new FileOutputStream(readWriteFile().toFile(), false).close();
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void filesGetOwner() throws IOException {
        Files.getOwner(readFile());
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void filesProbeContentType() throws IOException {
        Files.probeContentType(readFile());
    }

    @EntitlementTest(expectedAccess = PLUGINS)
    static void filesSetOwner() throws IOException {
        UserPrincipal owner = EntitledActions.getFileOwner(readWriteFile());
        Files.setOwner(readWriteFile(), owner); // set to existing owner, just trying to execute the method
    }

    private FileCheckActions() {}
}
