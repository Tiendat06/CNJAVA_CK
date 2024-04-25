package com.java.service.user;

public class TEMP {
    //    @Override
//    public ResponseEntity<byte[]> exportReportOldMethod(List<User> userList) {
//        byte[] pdfBytes = createPDFBytes(userList);
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "data.pdf");
//        headers.setContentLength(pdfBytes.length);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfBytes);
//    }
//
//    private byte[] createPDFBytes(List<User> userList) {
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
//            Document document = new Document(pdfDocument);
//
//
//            Table table = new Table(8);
//
//            // Add headers
//            table.addCell(createCell("ID", TextAlignment.CENTER));
//            table.addCell(createCell("First name", TextAlignment.CENTER));
//            table.addCell(createCell("Last name", TextAlignment.CENTER));
//            table.addCell(createCell("Email", TextAlignment.CENTER));
//            table.addCell(createCell("Phone number", TextAlignment.CENTER));
//            table.addCell(createCell("Address", TextAlignment.CENTER));
//            table.addCell(createCell("Birthday", TextAlignment.CENTER));
//            table.addCell(createCell("Gender", TextAlignment.CENTER));
//
//            // Add data rows
//            for (User user: userList){
//                table.addCell(createCell(user.getUser_id(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getFirst_name(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getLast_name(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getEmail(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getPhone_number(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getAddress(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getBirthday().toString(), TextAlignment.CENTER));
//                table.addCell(createCell(user.getGender(), TextAlignment.CENTER));
//            }
//
//            document.add(table);
//            document.close();
//
//            return outputStream.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private Cell createCell(String content, TextAlignment alignment) throws IOException {
//
//        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
//        Text text = new Text(content).setFont(font);
//        Paragraph paragraph = new Paragraph().add(text);
//
//        Cell cell = new Cell().add(paragraph);
//        cell.setTextAlignment(alignment);
//
//        return cell;
//    }
}
