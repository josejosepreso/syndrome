package com.pdf_generator_service.util;

public class Configuration {
	public static final String REPORT_BASE_HTML = """
		<html>
			<body style="font-family: Arial, sans-serif; font-size: 12px; color: #333;">
				<h1 style="text-align: center; color: #2c3e50;">Proxies found</h1>
				<table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
					<thead>
						<tr style="background-color: #f2f2f2;">
							<td style="border: 1px solid #ccc; padding: 6px;"><b>Id</b></td>
							<td style="border: 1px solid #ccc; padding: 6px;"><b>Ip</b></td>
							<td style="border: 1px solid #ccc; padding: 6px;"><b>Port</b></td>
							<td style="border: 1px solid #ccc; padding: 6px;"><b>Source</b></td>
							</tr>
					</thead>
					<tbody><!-- PROXIES_RECORDS_ROWS --></tbody>
				</table>
			</body>
		</html>
		""";


	public static final String PROXY_RECORD_TR_HTML = """
		<tr>
			<td style="border: 1px solid #ccc; padding: 6px;">%s</td>
			<td style="border: 1px solid #ccc; padding: 6px;">%s</td>
			<td style="border: 1px solid #ccc; padding: 6px;">%s</td>
			<td style="border: 1px solid #ccc; padding: 6px;">%s</td>
		</tr>
		""";

	public static final String PARAM_PROXIES_RECORDS = "<!-- PROXIES_RECORDS_ROWS -->";
}
