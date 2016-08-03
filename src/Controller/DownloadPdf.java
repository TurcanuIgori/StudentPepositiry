package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Model.Phone;
import Model.Student;

public class DownloadPdf {
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font columnFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	private static Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

	public void createPdf(HttpServletResponse response, List<Student> listStudents) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			addContent(document, listStudents);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addContent(Document document, List<Student> listStudents) throws DocumentException {
		Paragraph subPara = new Paragraph("Students List", subFont);
		subPara.setAlignment(Element.ALIGN_CENTER);
		createTable(subPara, listStudents);
		document.add(subPara);
	}

	private static void createTable(Paragraph subCatPart, List<Student> listStudents) throws DocumentException {
		PdfPTable table = new PdfPTable(8);
		float[] columnWidths = new float[] { 20f, 20f, 25f, 18f, 15f, 30f, 22f, 20f };
		table.setWidths(columnWidths);
		table.setWidthPercentage(100);

		PdfPCell c1 = new PdfPCell(new Phrase("Foto", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Numele", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Prenumele", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Date of Bithday", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Gender", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Adress", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Phone", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Library Abonament", columnFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);
		for (Student student : listStudents) {
			Image image1 = null;
			try {
				image1 = Image.getInstance(student.getPicture());
			} catch (FileNotFoundException a) {
				// TODO Auto-generated catch block
				a.printStackTrace();
				try {
					image1 = Image.getInstance("imgError.jpg");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			table.addCell(image1);
			table.addCell(new Phrase(student.getFirstName(), cellFont));
			table.addCell(new Phrase(student.getLastName(), cellFont));
			table.addCell(new Phrase(student.getDob().toString(), cellFont));
			table.addCell(new Phrase(student.getGenger(), cellFont));
			table.addCell(new Phrase(student.getAdress().getCountry() + " or. " + student.getAdress().getCity()
					+ " str. " + student.getAdress().getStreet(), cellFont));
			String phone = new String();
			for (Phone newPhone : student.getPhoneList()) {
				phone = phone + newPhone.getPhoneType().getName() + ": " + "\n" + newPhone.getNumber() + "\n";
			}
			table.addCell(new Phrase(phone, cellFont));
			table.addCell(new Phrase(student.getAbonament().getStatus().toString(), cellFont));
		}
		subCatPart.add(table);

	}
}
